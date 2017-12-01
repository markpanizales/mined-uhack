package com.pocketmarket.mined.utility;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.pocketmarket.mined.thread.ImageThread;

import java.util.UUID;

/**
 * Created by mark on 12/1/17.
 */

public class TCImageLoader implements ComponentCallbacks2 {
    private static final String TAG = "TCImageLoader";
    private final static String IMAGE_VIEW = "image_view";

    private TCLruCache cache;

    private ImageThread<String> mImageThread;

    private ImageView mImageview;

    private String mImageToken;

    public TCImageLoader(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        int maxKb = am.getMemoryClass() * 1024;
        int limitKb = maxKb / 8; // 1/8th of total ram
        cache = new TCLruCache(limitKb);

        initImage();
    }

    private void initImage(){
        mImageThread = new ImageThread<String>(new Handler());
        mImageThread.setListener(new ImageThread.Listener<String>() {

            @Override
            public void onImageResult(String s, String url, Bitmap bitmap, ImageView imageView, boolean isEnabled) {

                if (url != null && bitmap != null)
                    cache.put(url, bitmap);

                if (mImageview == null)
                    return;

                if (isEnabled){
                    mImageview.setImageBitmap(bitmap);
                }else{
                    mImageview.setImageBitmap(toGrayscale(bitmap));
                }

            }
        });

        mImageThread.start();
        mImageThread.getLooper();
        Log.i(TAG, "Background thread started for image");


    }

    public void display(String url, ImageView imageview, int defaultresource) {

        if (imageview != null)
            imageview.setImageResource(defaultresource);


        if (url == null || url.trim().equals(""))
            return;


        Bitmap image = cache.get(url);
        if (image != null) {
            imageview.setImageBitmap(image);
        }
        else {

            mImageToken = UUID.randomUUID().toString();
            mImageThread.queuePost(mImageToken, url, imageview, true);
//            new SetImageTask(imageview, true).execute(url);
        }
    }

    public void display(String url, ImageView imageview, int defaultresource, boolean isEnabled) {

        // check the imageview
        if (imageview != null)
            imageview.setImageResource(defaultresource);

        // check the url
        if (url == null || url.trim().equals(""))
            return;

        Bitmap image = cache.get(url);
        if (image != null) {

            if (isEnabled){
                imageview.setImageBitmap(image);
            }else{
                imageview.setImageBitmap(toGrayscale(image));
            }

        }else {
            mImageThread.queuePost(IMAGE_VIEW, url, imageview, isEnabled);
//            new SetImageTask(imageview, isEnabled).execute(url);
        }
    }


    private class TCLruCache extends LruCache<String, Bitmap> {

        public TCLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            int kbOfBitmap = value.getByteCount() / 1024;
            return kbOfBitmap;
        }
    }

//    private class SetImageTask extends AsyncTask<String, Void, Integer> {
//        private ImageView imageview;
//        private Bitmap bmp;
//        private boolean isEnabled;
//
//        public SetImageTask(ImageView imageview, boolean isEnabled) {
//            this.imageview = imageview;
//            this.isEnabled = isEnabled;
//        }
//
//        @Override
//        protected Integer doInBackground(String... params) {
//            String url = params[0];
//            try {
//                bmp = getBitmapFromURL(url);
//                if (bmp != null) {
//                    cache.put(url, bmp);
//                }
//                else {
//                    return 0;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                return 0;
//            }
//            return 1;
//        }
//
//        @Override
//        protected void onPostExecute(Integer result) {
//            if (result == 1) {
//                if (isEnabled){
//                    imageview.setImageBitmap(bmp);
//                }else{
//                    imageview.setImageBitmap(toGrayscale(bmp));
//                }
//
//
//            }
//            super.onPostExecute(result);
//        }
//
//        private Bitmap getBitmapFromURL(String src) {
//            try {
//                URL url = new URL(src);
//                HttpURLConnection connection
//                        = (HttpURLConnection) url.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                InputStream input = connection.getInputStream();
//                Bitmap myBitmap = BitmapFactory.decodeStream(input);
//                return myBitmap;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onTrimMemory(int level) {
        if (level >= TRIM_MEMORY_MODERATE) {
            cache.evictAll();
        }
        else if (level >= TRIM_MEMORY_BACKGROUND) {
            cache.trimToSize(cache.size() / 2);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {

    }

    @Override
    public void onLowMemory() {
        Log.e(TAG, "onLowMemory...");
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal){
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public void destroy(){
        if (mImageThread == null)
            return;

        mImageThread.quit();

    }

    public void destroyView(){
        if (mImageThread == null)
            return;

        mImageThread.clearQueue();
    }
}
