package com.pocketmarket.mined.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pocketmarket.mined.R;
import com.pocketmarket.mined.controllers.ButtonPlus;
import com.pocketmarket.mined.thread.PayPalPaymentThread;
import com.pocketmarket.mined.utility.AppApi;
import com.pocketmarket.mined.widget.CustomFontTextView;

import java.text.DecimalFormat;

/**
 * Created by mark on 12/1/17.
 */

public class PayFragment extends Fragment {
    private final static String TAG = "BuyFragment";
    private static final int AVATAR_SIZE = 200;
    private final static String PAYPAL_PAYMENT = "paypal_payment";

    private String mAccessToken;

    private CustomFontTextView mProductTitle;
    private CustomFontTextView mProductDescription;
    private CustomFontTextView mTotal;
    private ButtonPlus mBuyNow;
    private ImageView mProductAvatar;


    private int mId;
    private String mName;
    private String mDescription;
    private String mPhoto;
    private String mAmount;
    private int mType;

    private PayPalPaymentThread<String> mPayPalPaymentThread;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mId = getArguments().getInt("id");
            mName = getArguments().getString("name");
            mDescription = getArguments().getString("description");
            mPhoto = getArguments().getString("photo");
            mAmount = getArguments().getString("amount");
            mType = getArguments().getInt("type");
            mAccessToken = getArguments().getString("accessToken");

        }

        Log.i(TAG, "id: " + mId + ", name: " + mName + ", description: " + mDescription
                + ", photo: " + mPhoto + ", amount: " + mAmount + ", type: " + mType + ", mAccessToken: " + mAccessToken);

        initPayPalPayment();


    }

    private void initPayPalPayment(){
        mPayPalPaymentThread = new PayPalPaymentThread<String>(new Handler());
        mPayPalPaymentThread.setListener(new PayPalPaymentThread.Listener<String>() {

            @Override
            public void onPayPalPayment(String s, String result) {
                Log.i(TAG, "onPostExecute PayPalPaymentThread: " + result);

                mBuyNow.setEnabled(true);

                if (result == null)
                    return;

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(result));
                startActivity(i);


            }
        });

        mPayPalPaymentThread.start();
        mPayPalPaymentThread.getLooper();
        Log.i(TAG, "Background thread started for paymentProduct");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay, container, false);

        mTotal = (CustomFontTextView) view.findViewById(R.id.total_value);

        // format the amount to be more readable
        double amount = Double.parseDouble(mAmount);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String formatedAmount = formatter.format(amount);

        StringBuilder sbAmount = new StringBuilder();
        sbAmount.append("P ");
        sbAmount.append(formatedAmount);

        mTotal.setText(sbAmount.toString());

        mProductTitle = (CustomFontTextView) view.findViewById(R.id.product_title);
        mProductTitle.setText(mName);

        mProductDescription = (CustomFontTextView) view.findViewById(R.id.product_description);
        mProductDescription.setText(mDescription);

        mBuyNow = (ButtonPlus) view.findViewById(R.id.btn_buy);

        if (mType == 2) {
            mBuyNow.setText(getResources().getString(R.string.schedule_now));
        } else {
            mBuyNow.setText(getResources().getString(R.string.pay_now));
        }

        mBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payNow();
            }
        });

        mProductAvatar = (ImageView) view.findViewById(R.id.product_avatar);


        return view;
    }

    private void payNow() {

        mBuyNow.setEnabled(false);
        if (mType == 1) {
            paynowPaypal();
        } else {
            notification();
        }

    }

    private void paynowPaypal() {
        mPayPalPaymentThread.queuePost(PAYPAL_PAYMENT, getPaypalPayment(), mAccessToken, mId, mAmount);
    }

    /**
     * Url for the smart assistant
     *
     * @return
     */
    private String getPaypalPayment() {
        return AppApi.URL_NAME + AppApi.PAYPAL_PAYMENT;
    }

    private void notification() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPayPalPaymentThread == null)
            return;

        mPayPalPaymentThread.quit();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPayPalPaymentThread == null)
            return;

        mPayPalPaymentThread.clearQueue();
    }
}
