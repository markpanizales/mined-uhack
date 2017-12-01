package com.pocketmarket.mined.utility;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.facebook.login.LoginManager;

/**
 * Created by mark on 12/1/17.
 */

public class AppApi {
    public static final String TAG = "AppApi";

    // server link
    public static final String URL_NAME = "http://mined.ngrok.io/kuya-backend";

    // URL for the photos
    public final static String URL_PHOTOS_URL = "http://kinvo-staging.s3.amazonaws.com/uploads/photo/image/";

    public static final String LOGIN = "/api/auth";
    public static final String FIREBASE_SERVICE = "/u/firebaseservice";
    public static final String USER_INFO = "/api/user";

    // ai
    public static final String CHAT_REPLY = "/api/botreply";
    public static final String CHAT_SUGGEST = "/api/botsuggestion";

    // photo upload
    public static final String UPLOAD_PHOTO = "/api/uploadphoto";
    public static final String UPLOAD_PHOTO_FACE_DETECTION = "/api/uploadphotoface";

    // pdf upload
    public static final String UPLOAD_PDF = "/api/uploadpdf";

    // photo upload ocr text
    public static final String UPLOAD_PHOTO_OCR_TEXT = "/api/uploadphotoocrtext";

    // photo upload inception
    public static final String UPLOAD_PHOTO_INCEPTION = "/api/uploadphotoinception";

    // photo upload government id
    public static final String UPLOAD_GOVERNMENT_PHOTO = "/api/uploadgovernmentphoto";

    // products to pay
    public static final String PAYMENT_PRODUCT = "/api/paymentproduct";

    // Paypal
    public static final String PAYPAL_PAYMENT = "/api/paypal/payment";

    public static final String BALANCE_ANALYTICS = "/api/balanceanalytics";


    // items product
    public static final String ITEMS = "/api/itemsservice";

    // firebase
    public static final String PRODUCTS = "/api/products?accesstoken=";


    public static final String DEVICES = "/api/devices/devicefcm";

    // Application validation
    public static final String APPLICATION_VALIDATION = "/api/applicationvalidationservice";

    public static final String SHARED_PREFERENCE_NAME = "mined-prefs";

    public static void addFacebookTokenPreferences(Activity activity, String accessToken){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("fbAccessToken", accessToken);
        editor.apply();

    }

    // The method to logout facebook but not clear your shared preferences
    public static void facebookLogout() {
        Log.e(TAG, "facebookLogout....");
        LoginManager.getInstance().logOut();

    }

    public static void hideKeyBoard(Activity activity){
        View view = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public static String getUserInfo(String accessToken) {
        return URL_NAME + USER_INFO + accessToken;
    }

}
