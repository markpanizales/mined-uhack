package com.pocketmarket.mined.fetcher;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pocketmarket.mined.dto.ChatAssistantDetailsDTO;
import com.pocketmarket.mined.dto.UploadPhotoDTO;
import com.pocketmarket.mined.dto.UploadedFormDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 12/1/17.
 */

public class UploadPhotoFetchr extends MainFetchr {
    private static final String TAG = "UploadPhotoFetchr";

    public UploadPhotoDTO fetchItems(String sUrl, String avatar, String accessToken, String fileName) {
        UploadPhotoDTO uploadPhotoDTO = new UploadPhotoDTO();

        String response = null;
        try {

            Log.i(TAG, "URL: " + sUrl + ", avatar: " + avatar + ", accessToken: " + accessToken);

            // Creates the string to file
            File file = new File(avatar);

            // request of http post
            response = getUrl(sUrl, METHOD_TYPE_POST, file, accessToken, fileName);

            Log.i(TAG, "response: " + response);

            if (response == null)
                return null;

            // parse the string result from the parser
            JsonElement jsonElement = new JsonParser().parse(response);


            // get the json object response of the string
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if (jsonObject == null)
                return null;

            // get the id
            String id = jsonObject.get("id").getAsString();
            uploadPhotoDTO.setId(Integer.parseInt(id));

            // get the imageLink
            String imageLink = jsonObject.get("imageLink").getAsString();
            uploadPhotoDTO.setImageLink(imageLink);

            if (!jsonObject.get("message").isJsonNull()){
                String message = jsonObject.get("message").getAsString();
                uploadPhotoDTO.setMessage(message);
            }


            if (!jsonObject.get("uploadedForm").isJsonNull()){
                JsonObject uploadedFormObject = jsonObject.get("uploadedForm").getAsJsonObject();

                UploadedFormDTO uploadedFormDTO = new UploadedFormDTO();

                String uploadedFormid = uploadedFormObject.get("id").getAsString();
                uploadedFormDTO.setId(Integer.parseInt(uploadedFormid));

                String email = uploadedFormObject.get("email").getAsString();
                uploadedFormDTO.setEmail(email);

                String industry = uploadedFormObject.get("industry").getAsString();
                uploadedFormDTO.setIndustry(industry);

                String officeIndustry = uploadedFormObject.get("officeIndustry").getAsString();
                uploadedFormDTO.setOfficeIndustry(officeIndustry);

                String officeTelephone = uploadedFormObject.get("officeTelephone").getAsString();
                uploadedFormDTO.setOfficeTelephone(officeTelephone);

                String city = uploadedFormObject.get("city").getAsString();
                uploadedFormDTO.setCity(city);


                String birthDate = uploadedFormObject.get("birthDate").getAsString();
                uploadedFormDTO.setBirthDate(birthDate);

                String mothersMaidenName = uploadedFormObject.get("mothersmaidenname").getAsString();
                uploadedFormDTO.setMothersmaidenname(mothersMaidenName);

                String lastName = uploadedFormObject.get("lastname").getAsString();
                uploadedFormDTO.setLastname(lastName);

                String firstName = uploadedFormObject.get("firstname").getAsString();
                uploadedFormDTO.setFirstname(firstName);

                String civilStatus = uploadedFormObject.get("civilstatus").getAsString();
                uploadedFormDTO.setCivilstatus(civilStatus);

                String yearsincompany = uploadedFormObject.get("yearsincompany").getAsString();
                uploadedFormDTO.setYearsincompany(yearsincompany);

                String occupation = uploadedFormObject.get("occupation").getAsString();
                uploadedFormDTO.setOccupation(occupation);

                String officeCity = uploadedFormObject.get("officecity").getAsString();
                uploadedFormDTO.setOfficecity(officeCity);


                String tin = uploadedFormObject.get("tin").getAsString();
                uploadedFormDTO.setTin(tin);

                String sss = uploadedFormObject.get("sss").getAsString();
                uploadedFormDTO.setSss(sss);

                String authorizedrelation = uploadedFormObject.get("authorizedrelation").getAsString();
                uploadedFormDTO.setAuthorizedrelation(authorizedrelation);

                String authorizednumber = uploadedFormObject.get("authorizednumber").getAsString();
                uploadedFormDTO.setAuthorizednumber(authorizednumber);

                String carownership = uploadedFormObject.get("carownership").getAsString();
                uploadedFormDTO.setCarownership(carownership);

                String citizenship = uploadedFormObject.get("citizenship").getAsString();
                uploadedFormDTO.setCitizenship(citizenship);

                String homeaddress = uploadedFormObject.get("homeaddress").getAsString();
                uploadedFormDTO.setHomeaddress(homeaddress);

                String authorizedcontactperson = uploadedFormObject.get("authorizedcontactperson").getAsString();
                uploadedFormDTO.setAuthorizedcontactperson(authorizedcontactperson);

                String employer = uploadedFormObject.get("employer").getAsString();
                uploadedFormDTO.setEmployer(employer);

                String userid = uploadedFormObject.get("userid").getAsString();
                uploadedFormDTO.setUserid(Integer.parseInt(userid));

                // update the objects for the uploaded form
                uploadPhotoDTO.setUploadedForm(uploadedFormDTO);

            }


            String userId = jsonObject.get("userid").getAsString();
            uploadPhotoDTO.setUserId(Integer.parseInt(userId));

            if (!jsonObject.get("details").isJsonNull()) {
                JsonArray jsonArray = jsonObject.get("details").getAsJsonArray();

                List<ChatAssistantDetailsDTO> chatAssistantDetailsList = new ArrayList<ChatAssistantDetailsDTO>();
                for (int i = 0; i < jsonArray.size(); i++){
                    JsonObject detailsObject = jsonArray.get(i).getAsJsonObject();

                    ChatAssistantDetailsDTO chatAssistantDetails = new ChatAssistantDetailsDTO();

                    String detailsid = detailsObject.get("id").getAsString();
                    chatAssistantDetails.setId(Integer.parseInt(detailsid));

                    if (!detailsObject.get("name").isJsonNull()){
                        String name = detailsObject.get("name").getAsString();
                        chatAssistantDetails.setName(name);
                    }

                    if (!detailsObject.get("description").isJsonNull()){
                        String description = detailsObject.get("description").getAsString();
                        chatAssistantDetails.setDescription(description);
                    }

                    if (!detailsObject.get("image").isJsonNull()){
                        String image = detailsObject.get("image").getAsString();
                        chatAssistantDetails.setImage(image);
                    }

                    if (!detailsObject.get("url").isJsonNull()){
                        String urlLink = detailsObject.get("url").getAsString();
                        chatAssistantDetails.setUrl(urlLink);
                    }

                    if (!detailsObject.get("type").isJsonNull()){
                        String type = detailsObject.get("type").getAsString();
                        chatAssistantDetails.setType(type);
                    }

                    chatAssistantDetailsList.add(chatAssistantDetails);

                }

                uploadPhotoDTO.setDetails(chatAssistantDetailsList);

            }


        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch items: ", e);
        } catch (IllegalStateException e) {
            Log.e(TAG, "Failed parse items: ", e);

        }

        return uploadPhotoDTO;
    }

}
