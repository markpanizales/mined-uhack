package com.pocketmarket.mined.fetcher;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by mark on 12/1/17.
 */

public class ImageFetchr extends MainFetchr {
    private static final String TAG = "ImageFetchr";

    public Bitmap fetchItems(String sUrl) {
        Log.d(TAG, "init ImageFetchr...");

        return getBitmapFromURL(sUrl);

    }
}
