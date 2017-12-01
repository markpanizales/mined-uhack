package com.pocketmarket.mined.dto;

/**
 * Created by mark on 12/1/17.
 */

public class FirebaseUserDTO {
    private String mUserEmail;
    private String mEmail;

    public FirebaseUserDTO(String userEmail, String email) {
        mUserEmail = userEmail;
        mEmail = email;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public void setUserEmail(String userEmail) {
        mUserEmail = userEmail;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

}
