package com.pocketmarket.mined.fetcher;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pocketmarket.mined.dto.UploadedFormDTO;
import com.pocketmarket.mined.utility.Utils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by mark on 12/1/17.
 */

public class UpdateApplicationValidationPostFetchr extends MainFetchr {
    private static final String TAG = "UpdateApplicationValidationPostFetchr";
    private String ID = "id";

    private String EMAIL = "email";
    private String INDUSTRY = "industry";
    private String OFFICE_INDUSTRY = "officeindustry";
    private String OFFICE_TELEPHONE = "officetelephone";
    private String CITY = "city";
    private String BIRTH_DATE = "birthdate";
    private String MOTHERS_MAIDEN_NAME = "mothersmaidenname";
    private String LAST_NAME = "lastname";
    private String FIRST_NAME = "firstname";
    private String CIVIL_STATUS = "civilstatus";
    private String YEARS_IN_COMPANY = "yearsincompany";
    private String OCCUPATION = "occupation";
    private String OFFICE_CITY = "officecity";
    private String TIN = "tin";
    private String SSS = "sss";
    private String AUTHORIZED_RELATION = "authorizedrelation";
    private String AUTHORIZED_NUMBER = "authorizednumber";
    private String CAR_OWNERSHIP = "carownership";
    private String CITIZENSHIP = "citizenship";
    private String HOME_ADDRESS = "homeaddress";
    private String AUTHORIZED_CONTACT_PERSON = "authorizedcontactperson";
    private String EMPLOYER = "employer";


    public UploadedFormDTO fetchItems(String sUrl, UploadedFormDTO uploadedFormDTO, String accessToken) {

        String response = null;
        try{
            Log.i(TAG, "URL: " + sUrl + ", email: " + uploadedFormDTO.getEmail() + ", INDUSTRY: " +  uploadedFormDTO.getIndustry() + ", OFFICE_INDUSTRY: " + uploadedFormDTO.getOfficeIndustry()
                    + ", OFFICE_TELEPHONE: " + uploadedFormDTO.getOfficeTelephone() + ", CITY: " + uploadedFormDTO.getCity() + ", BIRTH_DATE: " + uploadedFormDTO.getBirthDate()
                    + ", MOTHERS_MAIDEN_NAME: " + uploadedFormDTO.getMothersmaidenname() + ", LAST_NAME: " + uploadedFormDTO.getLastname() + ", FIRST_NAME: " + uploadedFormDTO.getFirstname()
                    + ", CIVIL_STATUS: " + uploadedFormDTO.getCivilstatus() + ", YEARS_IN_COMPANY: " + uploadedFormDTO.getYearsincompany() + ", OCCUPATION: " + uploadedFormDTO.getOccupation()
                    + ", OFFICE_CITY: " + uploadedFormDTO.getOfficecity() + ", TIN: " + uploadedFormDTO.getTin() + ", SSS: " + uploadedFormDTO.getSss() + ", AUTHORIZED_RELATION: " + uploadedFormDTO.getAuthorizedrelation()
                    + ", AUTHORIZED_NUMBER: " + uploadedFormDTO.getAuthorizednumber() + ", CAR_OWNERSHIP: " + uploadedFormDTO.getCarownership() + ", CITIZENSHIP: " + uploadedFormDTO.getCitizenship()
                    + ", HOME_ADDRESS: " + uploadedFormDTO.getHomeaddress() + ", AUTHORIZED_CONTACT_PERSON: " + uploadedFormDTO.getAuthorizedcontactperson() + ", EMPLOYER: " + uploadedFormDTO.getEmployer()
                    + ", accessToken: " + accessToken);

            HashMap<String, String> params = new HashMap<>();
            params.put(ID, Integer.toString(uploadedFormDTO.getId()));
            params.put(EMAIL, uploadedFormDTO.getEmail());
            params.put(INDUSTRY, uploadedFormDTO.getIndustry());
            params.put(OFFICE_INDUSTRY, uploadedFormDTO.getOfficeIndustry());
            params.put(OFFICE_TELEPHONE, uploadedFormDTO.getOfficeTelephone());
            params.put(CITY, uploadedFormDTO.getCity());
            params.put(BIRTH_DATE, uploadedFormDTO.getBirthDate());
            params.put(MOTHERS_MAIDEN_NAME, uploadedFormDTO.getMothersmaidenname());
            params.put(LAST_NAME, uploadedFormDTO.getLastname());
            params.put(FIRST_NAME, uploadedFormDTO.getFirstname());
            params.put(CIVIL_STATUS, uploadedFormDTO.getCivilstatus());
            params.put(YEARS_IN_COMPANY, uploadedFormDTO.getYearsincompany());
            params.put(OCCUPATION, uploadedFormDTO.getOccupation());
            params.put(OFFICE_CITY, uploadedFormDTO.getOfficecity());
            params.put(TIN, uploadedFormDTO.getTin());
            params.put(SSS, uploadedFormDTO.getSss());
            params.put(AUTHORIZED_RELATION, uploadedFormDTO.getAuthorizedrelation());
            params.put(AUTHORIZED_NUMBER, uploadedFormDTO.getAuthorizednumber());
            params.put(CAR_OWNERSHIP, uploadedFormDTO.getCarownership());
            params.put(CITIZENSHIP, uploadedFormDTO.getCitizenship());
            params.put(HOME_ADDRESS, uploadedFormDTO.getHomeaddress());
            params.put(AUTHORIZED_CONTACT_PERSON, uploadedFormDTO.getAuthorizedcontactperson());
            params.put(EMPLOYER, uploadedFormDTO.getEmployer());

            // request of http post
            response = getUrl(sUrl, METHOD_TYPE_POST, params, accessToken);

            if (response == null)
                return null;

            if (!Utils.isJSONValid(response))
                return null;

            // parse the string result from the parser
            JsonElement jsonElement = new JsonParser().parse(response);

            // get the json object response of the string
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject == null)
                return null;




        }catch (IOException e) {
            Log.e(TAG, "Failed to fetch items: ", e);
        } catch (IllegalStateException e) {
            Log.e(TAG, "Failed parse items: ", e);
        }

        return uploadedFormDTO;

    }

}
