package com.pocketmarket.mined;

import android.content.Context;
import android.widget.ImageView;

import com.pocketmarket.mined.utility.TCImageLoader;

/**
 * Created by mark on 12/1/17.
 */

public class ImageCache {
    private static ImageCache instance;
    private TCImageLoader mTCImageLoader;
//    private static final int SIZE = 300;
//    public static final String RESIZER_PREFIX = "https://imgr.pocketmarket.com/unsafe/" + SIZE + "x/smart/";


    public ImageCache(Context context) {
        mTCImageLoader = new TCImageLoader(context);

    }

    public static ImageCache getInstance(Context context) {

        if (instance == null) {
            instance = new ImageCache(context);
        }

        return instance;
    }

    public void display(String url, ImageView imageview, int defaultresource) {
        mTCImageLoader.display(url, imageview, defaultresource);
        //mTCImageLoader.display(RESIZER_PREFIX + url, imageview, defaultresource);
    }

    public void display(String url, ImageView imageview, int defaultresource, boolean isEnabled) {
        mTCImageLoader.display(url, imageview, defaultresource, isEnabled);
        //mTCImageLoader.display(RESIZER_PREFIX + url, imageview, defaultresource, isEnabled);
    }

    public static void clearInstance() {
        instance = null;

    }

    public void destroy(){
        mTCImageLoader.destroy();
    }

    public void destroyView(){
        mTCImageLoader.destroyView();
    }


}

