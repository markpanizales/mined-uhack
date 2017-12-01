package com.pocketmarket.mined.fetcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pocketmarket.mined.dto.BalanceAnalyticsDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by mark on 12/1/17.
 */

public class BalanceAnalyticsGetFetchr extends MainFetchr {
    private static final String TAG = "BalanceAnalyticsGetFetchr";

    public ArrayList<BalanceAnalyticsDTO> fetchItems(String sUrl, String accesstoken){

        String response = null;

        // request of http post
        try {
            response = getUrl(sUrl, METHOD_TYPE_GET, null, accesstoken);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response == null)
            return null;

        // parse the string result from the parser
        JsonElement jsonElement = new JsonParser().parse(response);


        // get the json object response of the string
        JsonArray productsArray = jsonElement.getAsJsonArray();

        ArrayList<BalanceAnalyticsDTO> balanceAnalyticsList = new ArrayList<BalanceAnalyticsDTO>();

        for (int i = 0; i < productsArray.size(); i++) {
            JsonObject jsonObject = productsArray.get(i).getAsJsonObject();
            BalanceAnalyticsDTO balanceAnalytics = new BalanceAnalyticsDTO();

            String id = jsonObject.get("id").getAsString();
            balanceAnalytics.setId(Integer.parseInt(id));

            BigDecimal pesofixed = jsonObject.get("pesofixed").getAsBigDecimal();
            balanceAnalytics.setPesofixed(pesofixed);

            BigDecimal dollar = jsonObject.get("dollar").getAsBigDecimal();
            balanceAnalytics.setDollar(dollar);

            BigDecimal equity = jsonObject.get("equity").getAsBigDecimal();
            balanceAnalytics.setEquity(equity);

            balanceAnalyticsList.add(balanceAnalytics);

        }

        return balanceAnalyticsList;

    }

}
