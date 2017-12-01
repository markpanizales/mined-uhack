package com.pocketmarket.mined.controllers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pocketmarket.mined.dto.FirebaseUserDTO;
import com.pocketmarket.mined.dto.PostDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mark on 12/1/17.
 */

public class FirebaseController {
    private static final String TAG = "FirebaseController";

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private String mCustomToken;

    private Activity mActivity;

    private String mFirebaseToken;

    private FirebaseDatabase mDatabase;

    private DatabaseReference mRef;


    public interface OnFirebaseResult{
        public void onFirebaseSuccess();
        public void onFirebaseMessageSendComplete();
        public void onFirebaseMessageSendChatComplete();
    }

    OnFirebaseResult mOnFirebaseResult;

    public FirebaseController(Activity activity, OnFirebaseResult onFirebaseResult) {
        mActivity = activity;
        mOnFirebaseResult = onFirebaseResult;
    }

    public void initFirebase(){
        // Use properties to keep save the api keys
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = mActivity.getBaseContext().getAssets().open("firebase.properties");

            // load a properties file
            prop.load(input);

            Log.d(TAG, "getFirebase apiToken: " + prop.getProperty("apiToken") + ", apiUrl: " + prop.getProperty("apiUrl"));

            mFirebaseToken = prop.getProperty("apiToken");


        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        // initialize the database
        mDatabase = FirebaseDatabase.getInstance();
    }

    public void setCustomToken(String token) {
        mCustomToken = token;

        String status;
        if (mCustomToken != null) {
            status = "Token:" + mCustomToken;
        } else {
            status = "Token: null";
        }

        Log.d(TAG, "status: " + status);

        startSignin();

    }

    private void startSignin(){
        Log.d(TAG, "Custom Token: " + mCustomToken);

        mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCustomToken:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCustomToken", task.getException());
                            Toast.makeText(mActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        FirebaseUser user = task.getResult().getUser();

                        Log.i(TAG, "User ID: " + user.getUid() + ", user: " + user.getDisplayName() + ", user email: " + user.getEmail());

//                        sendMessage();

//                        DatabaseReference reference = mDatabase.getReference();
//                        reference.

                        //readMessage("message");

                        //readAllMessages();

                        mRef = mDatabase.getReference();

//                        String userId ="1234" ;
//                        String username = "test";
//                        String title = "this is a title test";
//                        String body = "this is a body test";
//
//                        writeNewPost(userId, username, title, body);

                        retrievePost();

                    }
                });
    }

    private void sendMessage(){

        mRef = mDatabase.getReference("message");
        mRef.setValue("Hello, World!");

        mRef = mDatabase.getReference("message2");
        mRef.setValue("Hello, World2!");
    }

    private void readMessage(String reference){
        mRef = mDatabase.getReference(reference);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

    }

    private void readAllMessages(){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot message: dataSnapshot.getChildren()){
                    String name = message.getValue(String.class);
                    Log.i(TAG, "name: " + name);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

    }

    public void writeNewUser(String userId, String name, String email){
        FirebaseUserDTO firebaseUserDTO = new FirebaseUserDTO(name, email);

        mRef.child("users").child(userId).setValue(firebaseUserDTO);

    }

    public void writeNewPost(String userId, String username, String title, String body){
        String key = mRef.child("posts").push().getKey();

        PostDTO postDTO = new PostDTO(userId, username, title, body);
        Map<String, Object> postValues = postDTO.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);

        mRef.updateChildren(childUpdates);

    }

    private void retrievePost(){

        mRef.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot message: dataSnapshot.getChildren()){
                    PostDTO postDTO = message.getValue(PostDTO.class);

                    String title = postDTO.toMap().get("title").toString();
                    Log.i(TAG, "name: " + title);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }
        });

    }

    public String getFirebaseToken() {
        return mFirebaseToken;
    }
}
