package com.pocketmarket.mined.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.pocketmarket.mined.R;
import com.pocketmarket.mined.RoundedImageView;
import com.pocketmarket.mined.activity.ApplicationActivity;
import com.pocketmarket.mined.adapter.ChatListViewAdapter;
import com.pocketmarket.mined.adapter.UserPhotoAdapter;
import com.pocketmarket.mined.data.AssistantFragmentManager;
import com.pocketmarket.mined.data.ProductPaymentManager;
import com.pocketmarket.mined.di.components.AssistantFragmentComponent;
import com.pocketmarket.mined.di.components.DaggerAssistantFragmentComponent;
import com.pocketmarket.mined.di.components.DaggerProductPaymentComponent;
import com.pocketmarket.mined.di.components.ProductPaymentComponent;
import com.pocketmarket.mined.di.modules.AssistantFragmentModule;
import com.pocketmarket.mined.di.modules.ProductPaymentModule;
import com.pocketmarket.mined.dto.ChatSuggestionDTO;
import com.pocketmarket.mined.dto.ChatSuggestionDetailsDTO;
import com.pocketmarket.mined.dto.MessageDTO;
import com.pocketmarket.mined.dto.UploadedFormDTO;
import com.pocketmarket.mined.utility.MarshMallowPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mark on 12/1/17.
 */

public class AssistantFragment extends Fragment implements ChatListViewAdapter.onChatPass, View.OnClickListener, UserPhotoAdapter.onUploadPhotoPass,
        TextToSpeech.OnInitListener {

    private final static String TAG = "AssistantFragment";
    private final static String UITF_INDIVIDUAL_APPLICATION = "UITF Individual Application";
    private final static String TAKE_A_SELFIE_FOR_REGISTRATION = "take a selfie";
    private final static String UPLOAD_A_GOVERNMENT_ID = "upload at least one government issued id";
    private final static String VALIDATING_SUBMITTED_APPLICATION = "validating your submitted application";
    private final static String INVALID_APPLICATION = "Invalid UITF form";

    // camera variable
    private final static int REQUEST_TAKE_PHOTO = 1;
    private final static int REQUEST_APPLICATION_VALIDATION = 2;

    // for speech recognition
    private static final int SPEECH_INPUT = 27;

    // for file upload
    private static final int FILE_UPLOAD_CODE = 37;


    private String mAccessToken;
    private RoundedImageView mProductImage;

    private ArrayList<MessageDTO> mFirebaseResult;

    // permision rules for marshmallow
    MarshMallowPermission mMarshMallowPermission;

    private int mUploadImageStatus;

    private String mCurrentPhotoPath;

    private TextToSpeech mTts;

    @BindView(R.id.chat_layout)
    LinearLayout mChatLayout;
    @BindView(R.id.progress_bar_chat)
    ProgressBar mProgressBarChat;
    @BindView(R.id.enter_message)
    EditText mEnterMessage;
    @BindView(R.id.custom_message)
    ImageView mCustomMessage;
    @BindView(R.id.camera) ImageView mCamera;
    @BindView(R.id.image_upload) ImageView mImageUpload;
    @BindView(R.id.file_upload) ImageView mFileUpload;
    @BindView(R.id.image_mic) ImageView mImageMic;
    @BindView(android.R.id.list)
    ListView mListView;
    @BindView(R.id.send_message) ImageView mSendMessage;
    @BindView(R.id.more) ImageView mMore;
    @BindView(R.id.message_layout) LinearLayout mMessageLayout;
    @BindView(R.id.image_gallery)
    RecyclerView mImageGallery;

    @Inject
    AssistantFragmentManager mAssistantFragmentManager;

    @Inject
    ProductPaymentManager mProductPaymentManager;

    private AssistantFragmentComponent mAssistantFragmentComponent;

    private ProductPaymentComponent mProductPaymentComponent;


    private AssistantFragmentComponent getAssistantFragmentComponent(){
        if (mAssistantFragmentComponent == null){
            mAssistantFragmentComponent = DaggerAssistantFragmentComponent
                    .builder()
                    .assistantFragmentModule(new AssistantFragmentModule(this))
                    .build();

        }

        return mAssistantFragmentComponent;
    }

    private ProductPaymentComponent getProductPaymentComponent(){
        if (mProductPaymentComponent == null){
            mProductPaymentComponent = DaggerProductPaymentComponent
                    .builder()
                    .productPaymentModule(new ProductPaymentModule(this))
                    .build();
        }

        return mProductPaymentComponent;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAssistantFragmentComponent().inject(this);

        getProductPaymentComponent();

        // assign the abstract class for chat and upload
        mAssistantFragmentManager.setOnChatPass(this);
        mAssistantFragmentManager.setOnUploadPhotoPass(this);

        // assign the enhance permission for marshmallow
        mMarshMallowPermission = mAssistantFragmentManager.getMarshMallowPermission();

        // assign the list of firebase data
        mFirebaseResult = mAssistantFragmentManager.getFirebaseResult();

        // get the shared preferences user value
        mAccessToken = mAssistantFragmentManager.getAccessToken();
        Log.i(TAG, "accessToken: " + mAccessToken);

        setUploadImageStatus(0);

        mTts = new TextToSpeech(getContext(), this);

    }

    /**
     * The method to set the status identity of upload image
     * @param imageStatus
     */
    private void setUploadImageStatus(int imageStatus){
        mUploadImageStatus = imageStatus;
        mAssistantFragmentManager.setUploadImageStatus(imageStatus);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assistant, container, false);
        ButterKnife.bind(this, view);

        // custom control can not detect by butterknife
        mProductImage = (RoundedImageView) view.findViewById(R.id.image_round);

        mAssistantFragmentManager.setObjects(mChatLayout,
                mProductImage,
                mProgressBarChat,
                mEnterMessage,
                mCustomMessage,
                mCamera,
                mImageMic,
                mListView,
                mSendMessage,
                mMore,
                mMessageLayout,
                mImageGallery,
                mTts);


        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Regular.ttf");
        mEnterMessage.setTypeface(tf);
        mEnterMessage.setHintTextColor(ContextCompat.getColor(getContext(), R.color.hint_text_color));

        mCustomMessage.setOnClickListener(this);

        mCamera.setOnClickListener(this);

        mImageUpload.setOnClickListener(this);

        mFileUpload.setOnClickListener(this);

        mImageMic.setOnClickListener(this);

        mSendMessage.setOnClickListener(this);

        mMore.setOnClickListener(this);

        // image browser list
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mImageGallery.setLayoutManager(layoutManager);

        // disable the chat message. Perform the thread queuing first
        mAssistantFragmentManager.disableMessage(true);

        // create the gallery layout
        mAssistantFragmentManager.setupImageGallery();

        mAssistantFragmentManager.userQueuePost(mAccessToken);

        return view;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "This Language is not supported for text to speech");
            }

        } else {
            Log.e(TAG, "Initilization Failed! Check text to speech");
        }

    }

    /**
     * Check for empty message
     * @return
     */
    private boolean validateComment() {
        if (TextUtils.isEmpty(mEnterMessage.getText())) {
            mSendMessage.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
            return false;
        }

        return true;
    }

    /**
     * The method to send a message to the chat
     */
    private void fireBaseSendMessage(){
        String messageSend = mEnterMessage.getText().toString();

        setUploadImageStatus(0);

        mAssistantFragmentManager.fireBaseSendMessage(messageSend);

    }

    @Override
    public void onButtonClick(int id, String name, int status, int productId) {
        Log.i(TAG, "Assistant button id click: " + id + ", name: " + name + ", status: " + status + ", productId: " + productId);

        // The status for the button to behave. Perform smart suggestion or payment
        switch (status){
            case 0: // uitf individual application
                actionServices(id, name, productId);
                break;

            case 2: // payment redirection
                // Use the productId as the id of the product
                paymentInvoice(productId);
                break;

            default:
                setSmartSuggestMessage(name);
                break;
        }


    }

    @Override
    public void onImageClick(int id, String name, String url) {
        Log.i(TAG, "Assistant image id click: " + id + ", name: " + name + ", url: " + url);

        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));

        startActivity(i);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.custom_message:
                mAssistantFragmentManager.showKeyboard();
                break;

            case R.id.camera:
                mAssistantFragmentManager.showCamera();
                break;

            case R.id.image_upload:
                mAssistantFragmentManager.showImageUpload();
                break;

            case R.id.file_upload:
                mAssistantFragmentManager.showFileUpload();
                break;

            case R.id.image_mic:
                mAssistantFragmentManager.setVoice(true);
                mAssistantFragmentManager.showImageMic();
                break;

            case R.id.more:
                mAssistantFragmentManager.showMore();
                break;

            case R.id.send_message:
                if (validateComment()) {
                    fireBaseSendMessage();
                }
                break;

        }

    }

    @Override
    public void onUploadComplete(String message, UploadedFormDTO uploadedFormDTO, ArrayList<ChatSuggestionDetailsDTO> suggestionDetailList) {
        Log.d(TAG, "Upload complete....mUploadImageStatus: " + mUploadImageStatus);

        switch (mUploadImageStatus){
            case 0: // image recognition using inception
                Log.d(TAG, "inception results reply");

                if (message == null || message.length() < 1){
                    Log.d(TAG, "message is null...means unknown result for image recognition");
                    break;
                }


                if (suggestionDetailList == null){
                    mFirebaseResult.add(mAssistantFragmentManager.addSmartReply(message));

                }else{

                    ChatSuggestionDTO chatSuggestionDTO = new ChatSuggestionDTO();
                    chatSuggestionDTO.setId(1);
                    chatSuggestionDTO.setName(message);
                    chatSuggestionDTO.setDescription(message);
                    chatSuggestionDTO.setDetails(suggestionDetailList);

                    mFirebaseResult.add(mAssistantFragmentManager.addSmartSuggestionObjects(message, chatSuggestionDTO));

                }

                mAssistantFragmentManager.setupAdapter(mFirebaseResult);
                break;

            case 1: // upload for the registration using image
                Log.d(TAG, "apply for registration using image");
                setUploadImageStatus(2);
                validateUpload(uploadedFormDTO);
                break;

            case 2:
                Log.d(TAG, "upload selfie is complete");
                setUploadImageStatus(3);
                setSmartSuggestMessage(UPLOAD_A_GOVERNMENT_ID);
                break;

            case 3:
                Log.d(TAG, "upload valid government id...set the status to government id");
                setUploadImageStatus(5);
                requirementsValidation();
                break;

            case 4: // uitf registration must be in pdf and not image
                Log.d(TAG, "apply for uitf individual registration");
                setSmartSuggestMessage(INVALID_APPLICATION);
                break;

            default:
                setUploadImageStatus(0);
                break;
        }
    }

    @Override
    public void onUploadPDFComplete(String message, UploadedFormDTO uploadedFormDTO, ArrayList<ChatSuggestionDetailsDTO> suggestionDetailList) {
        Log.d(TAG, "Upload PDF complete....mUploadImageStatus: " + mUploadImageStatus);

        switch (mUploadImageStatus){
            case 4: // upload for the individual uitf registration
                Log.d(TAG, "apply for uitf individual registration");
                setUploadImageStatus(2);
                confirmValidation();
                break;

        }

    }

    /**
     * Call the items thread and search for its details.
     */
    private void paymentInvoice(int id){
        Log.i(TAG, "Payment Invoice id: " + id + ", accessToken: " + mAccessToken);

        // perform items thread and redirect to buyer view.
        mAssistantFragmentManager.initPaymentInvoice(id, mAccessToken);

    }


    /**
     * The method used for registration form validation
     * @param uploadedFormDTO
     */
    private void validateUpload(UploadedFormDTO uploadedFormDTO){

        if (uploadedFormDTO == null)
            return;

        String email = uploadedFormDTO.getEmail();

        Log.d(TAG, "email: " + email);

        Intent i = new Intent(getActivity(), ApplicationActivity.class);
        i.putExtra("uploadedForm", uploadedFormDTO);
        i.putExtra("accessToken", mAccessToken);

        startActivityForResult(i, REQUEST_APPLICATION_VALIDATION);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        Log.i(TAG, "requestCode: " + requestCode);

        switch (requestCode){
            case REQUEST_TAKE_PHOTO:

                mCurrentPhotoPath = mAssistantFragmentManager.getCurrentPhotoPath();

                // make it available to your media provider
                galleryAddPic();

                // basic image upload
                uploadImageUsingCamera();
                break;

            case REQUEST_APPLICATION_VALIDATION:
                String response = data.getStringExtra(ApplicationFragment.EXTRA_VALIDATION_RESULT);

                if (response != null){
                    // registration form validation for application
                    confirmValidation();
                }
                break;

            case SPEECH_INPUT:
                if (data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.d(TAG, "SPEECH_INPUT onActivityResult: " + result.get(0));
                    processSpeech(result.get(0));
                }
                break;

            case FILE_UPLOAD_CODE:
                if (data != null){
                    Uri uri = data.getData();
                    String filePath = uri.getPath();

                    // mark the file as pdf to avoid invalid json response
                    mAssistantFragmentManager.uploadPDF(filePath);
                }
                break;


        }

    }

    /**
     * Method to invoke the system media scanner to add your photo to the Media Provider
     */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }


    /**
     * The method for validating the registration and taking a selfie for KYC
     */
    private void confirmValidation(){
        Log.d(TAG, "Validation has been confirmed...");

        mAssistantFragmentManager.sendSmartSuggest(TAKE_A_SELFIE_FOR_REGISTRATION);


    }

    /**
     * Process speech text, send it to Brain and receive response. Then do task based on response.
     *
     * @param speech
     */
    public void processSpeech(final String speech) {

        mFirebaseResult.add(mAssistantFragmentManager.addMerchantReply(speech));
        mAssistantFragmentManager.setupAdapter(mFirebaseResult);

        mAssistantFragmentManager.setMessageSend(speech);
        mAssistantFragmentManager.assistantReply();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mAssistantFragmentManager.destroy();

        mProductPaymentManager.destroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mAssistantFragmentManager.destroyView();

        mProductPaymentManager.destroyView();
    }

    /**
     *
     * @param id
     * @param name
     * @param productId
     */
    private void actionServices(int id, String name, int productId){

        switch (productId){
            case -1: // uitf individual application
                setUploadImageStatus(4);
                setSmartSuggestMessage(UITF_INDIVIDUAL_APPLICATION);
                break;

            case -4: // uitf show charts analytics
                mAssistantFragmentManager.showAnalytics();
                break;

            case 0: // paypal payment after registration
                paypalPayments();
                break;
        }

    }

    /**
     * The method using the camera and upload it
     */
    private void uploadImageUsingCamera(){

        if (mCurrentPhotoPath == null || mCurrentPhotoPath.equals(""))
            return;

        if (mUploadImageStatus == 2){
            mAssistantFragmentManager.uploadImageFaceDetection(mCurrentPhotoPath);
        }else{
            mAssistantFragmentManager.uploadImage(mCurrentPhotoPath);
        }
    }

    private void requirementsValidation(){
        setSmartSuggestMessage(VALIDATING_SUBMITTED_APPLICATION);
    }

    private void setSmartSuggestMessage(String name){
        mAssistantFragmentManager.setMessageSend(name);
        mAssistantFragmentManager.smartSuggest();
    }

    /**
     * The method to redirect to payments
     */
    private void paypalPayments(){
        Log.d(TAG, "Paypal Payments processing for view...");
        mProductPaymentManager.processPayment("1");

    }

}