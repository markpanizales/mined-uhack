package com.pocketmarket.mined.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.pocketmarket.mined.fetcher.PayPalPostFetchr;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mark on 12/1/17.
 */

public class PayPalPaymentThread<Token> extends HandlerThread {
    private static final String TAG = "PayPalPaymentThread";
    private static final int MESSAGE_POST = 0;

    Handler mHandler;
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());
    Handler mResponseHandler;
    Listener<Token> mListener;

    private String mAccessToken;
    private int mId;
    private String mAmount;

    public interface Listener<Token>{
        void onPayPalPayment(Token token, String result);
    }

    public void setListener(Listener<Token> listener){
        mListener = listener;
    }

    public PayPalPaymentThread(Handler responseHandler) {
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

    public void queuePost(Token token, String url, String accessToken, int id, String amount){
        Log.i(TAG, "Got a URL: " + url + ", accessToken: " + accessToken + ", id: " + id + ", amount: " + amount);
        requestMap.put(token, url);

        mId = id;

        mAmount = amount;

        mAccessToken = accessToken;

        if (mHandler == null)
            return;


        mHandler.obtainMessage(MESSAGE_POST, token).sendToTarget();
    }

    private void handleRequest(final Token token){
        final String url = requestMap.get(token);
        if (url == null)
            return;

        final String result = new PayPalPostFetchr().fetchItems(url, mAccessToken, mId, mAmount);

        mResponseHandler.post(new Runnable() {
            @Override
            public void run() {
                if (requestMap.get(token) != url)
                    return;

                requestMap.remove(token);
                mListener.onPayPalPayment(token, result);
            }
        });

    }

    public void clearQueue(){
        mHandler.removeMessages(MESSAGE_POST);
        requestMap.clear();
    }

}

