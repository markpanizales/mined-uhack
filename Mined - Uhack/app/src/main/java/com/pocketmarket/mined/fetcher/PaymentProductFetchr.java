package com.pocketmarket.mined.fetcher;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pocketmarket.mined.dto.PaymentProductDTO;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by mark on 12/1/17.
 */

public class PaymentProductFetchr extends MainFetchr{
    private static final String TAG = "PaymentProductFetchr";
    private static final String MESSAGE = "message";

    public PaymentProductDTO fetchItems(String url, String message, String accesstoken){
        PaymentProductDTO paymentProduct = new PaymentProductDTO();

        try {

            HashMap<String, String> params = new HashMap<>();
            params.put(MESSAGE, message);

            String response = getUrl(url, METHOD_TYPE_POST, params, accesstoken);
            Log.i(TAG, "Received json: " + response);

            if (response == null || response.equals(""))
                return null;

            // parse the string result from the parser
            JsonElement jsonElement = new JsonParser().parse(response);

            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String id = jsonObject.get("id").getAsString();
            paymentProduct.setId(Integer.parseInt(id));

            String name = jsonObject.get("name").getAsString();
            paymentProduct.setName(name);

            String description = jsonObject.get("description").getAsString();
            paymentProduct.setDescription(description);

            String photo = jsonObject.get("photo").getAsString();
            paymentProduct.setPhoto(photo);

            String amount = jsonObject.get("amount").getAsString();
            paymentProduct.setAmount(amount);

            String type = jsonObject.get("type").getAsString();
            paymentProduct.setType(Integer.parseInt(type));


        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        }

        return paymentProduct;

    }

}
