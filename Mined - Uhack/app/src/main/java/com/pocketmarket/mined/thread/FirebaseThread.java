package com.pocketmarket.mined.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.pocketmarket.mined.dto.FirebaseDTO;
import com.pocketmarket.mined.fetcher.FirebasePostFetchr;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mark on 12/1/17.
 */

public class FirebaseThread<Token> extends HandlerThread {
    private static final String TAG = "FirebaseThread";
    private static final int MESSAGE_POST = 0;

    Handler mHandler;
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());
    Handler mResponseHandler;
    Listener<Token> mListener;

    private String mUid;

    public interface Listener<Token>{
        void onFirebaseThread(Token token, FirebaseDTO firebaseResultDTO);
    }

    public void setListener(Listener<Token> listener){
        mListener = listener;
    }

    public FirebaseThread(Handler responseHandler) {
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

    public void queuePost(Token token, String url, String uid){

        Log.i(TAG, "Got a URL: " + url);
        requestMap.put(token, url);

        mUid = uid;

        if (mHandler == null)
            return;


        mHandler.obtainMessage(MESSAGE_POST, token).sendToTarget();
    }

    private void handleRequest(final Token token){

        final String url = requestMap.get(token);
        if (url == null)
            return;


        final FirebaseDTO firebaseDTO = new FirebasePostFetchr().fetchItems(url, mUid);

        mResponseHandler.post(new Runnable() {
            @Override
            public void run() {
                if (requestMap.get(token) != url)
                    return;

                requestMap.remove(token);
                mListener.onFirebaseThread(token, firebaseDTO);
            }
        });


    }

}
