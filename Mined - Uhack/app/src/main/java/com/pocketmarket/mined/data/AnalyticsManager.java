package com.pocketmarket.mined.data;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.pocketmarket.mined.activity.ExpenseAnalyticsActivity;
import com.pocketmarket.mined.dto.BalanceAnalyticsDTO;
import com.pocketmarket.mined.fetcher.BalanceAnalyticsGetFetchr;
import com.pocketmarket.mined.utility.AppApi;

import java.util.ArrayList;

/**
 * Created by mark on 12/1/17.
 */

public class AnalyticsManager {
    private ArrayList<String> mPesoFixed = new ArrayList<String>();
    private ArrayList<String> mDollar = new ArrayList<String>();
    private ArrayList<String> mEquity = new ArrayList<String>();

    private String mAccessToken;

    private final static String TAG = "AnalyticsManager";

    private Activity mActivity;

    public AnalyticsManager(Activity activity, String accessToken) {

        mActivity = activity;

        mAccessToken = accessToken;


    }

    public void initExpenseAnalytics(){
        new BalanceAnalyticsFetchr().execute(getBalanceAnalytics());
    }

    /**
     * Url for the smart assistant
     * @return
     */
    private String getBalanceAnalytics(){
        return AppApi.URL_NAME + AppApi.BALANCE_ANALYTICS;
    }

    /**
     *  Asynccall for the assistant
     */
    private class BalanceAnalyticsFetchr extends AsyncTask<String, Void, ArrayList<BalanceAnalyticsDTO>> {

        @Override
        protected ArrayList<BalanceAnalyticsDTO> doInBackground(String... url) {
            Log.i(TAG, "BalanceAnalyticsFetchr URL: " + url[0]);
            return new BalanceAnalyticsGetFetchr().fetchItems(url[0], mAccessToken);
        }

        @Override
        protected void onPostExecute(ArrayList<BalanceAnalyticsDTO> balanceAnalyticsList) {
            Log.i(TAG, "onPostExecute BalanceAnalyticsGetFetchr: " + balanceAnalyticsList.size());

            if (mActivity == null)
                return;

            mPesoFixed = new ArrayList<String>();
            mDollar = new ArrayList<String>();
            mEquity = new ArrayList<String>();
            for (BalanceAnalyticsDTO balanceAnalyticsDTO: balanceAnalyticsList){
                String pesofixed = balanceAnalyticsDTO.getPesofixed().toString();
                String dollar = balanceAnalyticsDTO.getDollar().toString();
                String equity = balanceAnalyticsDTO.getEquity().toString();

                mPesoFixed.add(pesofixed);
                mDollar.add(dollar);
                mEquity.add(equity);


            }

        }

    }

    /**
     * show the analytics
     */
    public void showAnalytics(){

        Intent i = new Intent(mActivity, ExpenseAnalyticsActivity.class);
        i.putStringArrayListExtra("pesofixed", mPesoFixed);
        i.putStringArrayListExtra("dollar", mDollar);
        i.putStringArrayListExtra("equity", mEquity);
        mActivity.startActivity(i);
    }



}
