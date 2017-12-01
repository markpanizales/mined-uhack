package com.pocketmarket.mined.thread;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.pocketmarket.mined.fetcher.ImageFetchr;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mark on 12/1/17.
 */

public class ImageThread<Token> extends HandlerThread {
    private static final String TAG = "ImageThread";
    private static final int MESSAGE_POST = 0;

    Handler mHandler;
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());
    Handler mResponseHandler;
    Listener<Token> mListener;

    private ImageView mImageView;

    private boolean isEnabled;

    public interface Listener<Token>{
        void onImageResult(Token token, String url, Bitmap bitmap, ImageView imageView, boolean isEnabled);
    }

    public void setListener(Listener<Token> listener){
        mListener = listener;
    }

    public ImageThread(Handler responseHandler) {
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
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token) + ", token: " + token.toString());
                    handleRequest(token);
                }
            }
        };

    }

    public void queuePost(Token token, String url, ImageView imageView, boolean isEnabled){
        Log.i(TAG, "queuePost Got a URL: " + url + ", imageView: " + imageView + ", isEnabled: " + isEnabled);

        if (url == null || url.trim().equals("null"))
            return;

        requestMap.put(token, url);

        mImageView = imageView;

        this.isEnabled = isEnabled;

        if (mHandler == null)
            return;


        mHandler.obtainMessage(MESSAGE_POST, token).sendToTarget();
    }

    private void handleRequest(final Token token){
        final String url = requestMap.get(token);
        if (url == null)
            return;

        final Bitmap bitmap = new ImageFetchr().fetchItems(url);

        mResponseHandler.post(new Runnable() {
            @Override
            public void run() {
                if (requestMap.get(token) != url)
                    return;

                requestMap.remove(token);
                mListener.onImageResult(token, url, bitmap, mImageView, isEnabled);
            }
        });

    }

    public void clearQueue(){
        mHandler.removeMessages(MESSAGE_POST);
        requestMap.clear();
    }

}
