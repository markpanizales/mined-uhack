package com.pocketmarket.mined.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.pocketmarket.mined.dto.PaymentProductDTO;
import com.pocketmarket.mined.fetcher.PaymentProductFetchr;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mark on 12/1/17.
 */

public class PaymentProductThread<Token> extends HandlerThread {
    private static final String TAG = "PaymentProductThread";
    private static final int MESSAGE_POST = 0;

    Handler mHandler;
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());
    Handler mResponseHandler;
    Listener<Token> mListener;

    private String mAccessToken;
    private String mMessage;

    public interface Listener<Token>{
        void onPaymentProductThread(Token token, PaymentProductDTO paymentProductDTO);
    }

    public void setListener(Listener<Token> listener){
        mListener = listener;
    }

    public PaymentProductThread(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_POST){
                    Token token = (Token) msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token));
                    handleRequest(token);
                }
            }
        };

    }

    public void queuePost(Token token, String url, String message, String accessToken){
        Log.i(TAG, "Got a URL: " + url + ", accessToken: " + accessToken);
        requestMap.put(token, url);

        mMessage = message;

        mAccessToken = accessToken;

        if (mHandler == null)
            return;


        mHandler.obtainMessage(MESSAGE_POST, token).sendToTarget();
    }

    private void handleRequest(final Token token){
        final String url = requestMap.get(token);
        if (url == null)
            return;

        final PaymentProductDTO paymentProductDTO = new PaymentProductFetchr().fetchItems(url, mMessage, mAccessToken);

        mResponseHandler.post(new Runnable() {
            @Override
            public void run() {
                if (requestMap.get(token) != url)
                    return;

                requestMap.remove(token);
                mListener.onPaymentProductThread(token, paymentProductDTO);
            }
        });

    }

    public void clearQueue(){
        mHandler.removeMessages(MESSAGE_POST);
        requestMap.clear();
    }

}
