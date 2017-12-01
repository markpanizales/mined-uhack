package com.pocketmarket.mined.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.pocketmarket.mined.activity.PayActivity;
import com.pocketmarket.mined.di.ApplicationActivity;
import com.pocketmarket.mined.di.ApplicationContext;
import com.pocketmarket.mined.di.ApplicationFragment;
import com.pocketmarket.mined.di.SharedReference;
import com.pocketmarket.mined.dto.PaymentProductDTO;
import com.pocketmarket.mined.thread.PaymentProductThread;
import com.pocketmarket.mined.utility.AppApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.pocketmarket.mined.utility.AppApi.URL_NAME;

/**
 * Created by mark on 12/1/17.
 */
@Singleton
public class ProductPaymentManager {
    private final static String TAG = "ProductPaymentManager";
    private final static String PAYMENT_PRODUCT = "payment_product";

    private Context mContext;
    private Activity mActivity;
    private Fragment mFragment;
    private SharedPrefReference mSharedPrefReference;
    private String mAccessToken;

    private PaymentProductThread<String> mPaymentProductThread;

    @Inject
    public ProductPaymentManager(@ApplicationContext Context context,
                                 @ApplicationActivity Activity activity,
                                 @SharedReference SharedPrefReference sharedPrefReference,
                                 @ApplicationFragment Fragment fragment) {

        mContext = context;
        mActivity = activity;
        mSharedPrefReference = sharedPrefReference;
        mFragment = fragment;

        mAccessToken = mSharedPrefReference.getUserAccessToken();

        initPaymentProduct();


    }

    private void initPaymentProduct() {
        mPaymentProductThread = new PaymentProductThread<String>(new Handler());
        mPaymentProductThread.setListener(new PaymentProductThread.Listener<String>() {

            @Override
            public void onPaymentProductThread(String s, PaymentProductDTO paymentProductDTO) {
                if (paymentProductDTO == null) {
                    Log.d(TAG, "PaymentProduct is empty....");
                    return;
                }

                if (mActivity == null)
                    return;

                showPayment(paymentProductDTO);

            }
        });

        mPaymentProductThread.start();
        mPaymentProductThread.getLooper();
        Log.i(TAG, "Background thread started for paymentProduct");

    }

    /**
     * Url for the smart assistant
     *
     * @return
     */
    private String getPaymentProduct() {
        return URL_NAME + AppApi.PAYMENT_PRODUCT;
    }


    public void processPayment(String message) {

        mPaymentProductThread.queuePost(PAYMENT_PRODUCT, getPaymentProduct(), message, mAccessToken);

    }

    private void showPayment(PaymentProductDTO paymentProduct) {
        Intent i = new Intent(mActivity, PayActivity.class);
        i.putExtra("id", paymentProduct.getId());
        i.putExtra("name", paymentProduct.getName());
        i.putExtra("description", paymentProduct.getDescription());
        i.putExtra("photo", paymentProduct.getPhoto());
        i.putExtra("amount", paymentProduct.getAmount());
        i.putExtra("type", paymentProduct.getType());
        i.putExtra("accessToken", mAccessToken);
        mFragment.startActivity(i);

    }

    public void destroy() {
        if (mPaymentProductThread == null)
            return;

        mPaymentProductThread.quit();
    }


    public void destroyView(){
        if (mPaymentProductThread == null)
            return;

        mPaymentProductThread.clearQueue();
    }


}
