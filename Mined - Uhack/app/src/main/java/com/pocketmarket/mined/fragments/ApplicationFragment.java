package com.pocketmarket.mined.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.pocketmarket.mined.R;
import com.pocketmarket.mined.dto.UploadedFormDTO;
import com.pocketmarket.mined.fetcher.UpdateApplicationValidationPostFetchr;
import com.pocketmarket.mined.utility.AppApi;

/**
 * Created by mark on 12/1/17.
 */

public class ApplicationFragment extends Fragment {
    private final static String TAG = "ApplicationFragment";
    public final static String EXTRA_VALIDATION_RESULT = "com.pocketmarket.mined.application.validation";

    private Spinner mEmailSelection;
    private EditText mEmailText;

    private Spinner mIndustrySelection;
    private EditText mIndustryText;

    private Spinner mOfficeIndustrySelection;
    private EditText mOfficeIndustryText;

    private Spinner mOfficeTelephoneSelection;
    private EditText mOfficeTelephoneText;

    private Spinner mCitySelection;
    private EditText mCityText;

    private Spinner mSalutationSelection;
    private EditText mSalutationText;

    private Spinner mGenderSelection;
    private EditText mGenderText;

    private Spinner mBirthdateSelection;
    private EditText mBirthdateText;

    private Spinner mHomeOwnershipSelection;
    private EditText mHomeOwnershipText;

    private Spinner mHomeOwnershipYearsSelection;
    private EditText mHomeOwnershipYearsText;

    private Spinner mHomeOwnershipSalarySelection;
    private EditText mHomeOwnershipSalaryText;

    private Spinner mMothersMaidenNameSelection;
    private EditText mMothersMaidenNameText;

    private Spinner mLastNameSelection;
    private EditText mLastNameText;

    private Spinner mFirstNameSelection;
    private EditText mFirstNameText;

    private Spinner mCivilStatusSelection;
    private EditText mCivilStatusText;

    private Spinner mYearsInCompanySelection;
    private EditText mYearsInCompanyText;

    private Spinner mOccupationSelection;
    private EditText mOccupationText;

    private Spinner mOfficeCitySelection;
    private EditText mOfficeCityText;

    private Spinner mTinSelection;
    private EditText mTinText;

    private Spinner mSSSSelection;
    private EditText mSSSText;

    private Spinner mAuthorizedRelationSelection;
    private EditText mAuthorizedRelationText;

    private Spinner mAuthorizedNumberSelection;
    private EditText mAuthorizedNumberText;

    private Spinner mCarOwnerShipSelection;
    private EditText mCarOwnerShipText;

    private Spinner mCitizenshipSelection;
    private EditText mCitizenshipText;

    private Spinner mHomeAddressSelection;
    private EditText mHomeAddressText;

    private Spinner mAuthorizedContactPersonSelection;
    private EditText mAuthorizedContactPersonText;

    private Spinner mEmployerSelection;
    private EditText mEmployerText;

    private UploadedFormDTO mUploadedForm;

    private int mId;
    private String mEmail;
    private String mIndustry;
    private String mOfficeIndustry;
    private String mOfficeTelephone;
    private String mCity;
    private String mSalutation;
    private String mGender;
    private String mBirthdate;
    private String mHomeOwnership;
    private String mHomeOwnershipYears;
    private String mHomeOwnershipSalary;
    private String mMothersMaidenName;
    private String mLastName;
    private String mFirstName;
    private String mCivilStatus;
    private String mYearsInCompany;
    private String mOccupation;
    private String mOfficeCity;
    private String mTin;
    private String mSSS;
    private String mAuthorizedRelation;
    private String mAuthorizedNumber;
    private String mCarOwnerShip;
    private String mCitizenship;
    private String mHomeAddress;
    private String mAuthorizedContactPerson;
    private String mEmployer;

    private String mAccessToken;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mUploadedForm = (UploadedFormDTO) getArguments().getSerializable("uploadedForm");

        mAccessToken = getArguments().getString("accessToken");

        mId = mUploadedForm.getId();

        mEmail = mUploadedForm.getEmail();

        mIndustry = mUploadedForm.getIndustry();

        mOfficeIndustry = mUploadedForm.getOfficeIndustry();

        mOfficeTelephone = mUploadedForm.getOfficeTelephone();

        mCity = mUploadedForm.getCity();

        mBirthdate = mUploadedForm.getBirthDate();

        mMothersMaidenName = mUploadedForm.getMothersmaidenname();

        mLastName = mUploadedForm.getLastname();

        mFirstName = mUploadedForm.getFirstname();

        mCivilStatus = mUploadedForm.getCivilstatus();

        mYearsInCompany = mUploadedForm.getYearsincompany();

        mOccupation = mUploadedForm.getOccupation();

        mOfficeCity = mUploadedForm.getOfficecity();

        mTin = mUploadedForm.getTin();

        mSSS = mUploadedForm.getSss();

        mAuthorizedRelation = mUploadedForm.getAuthorizedrelation();

        mAuthorizedNumber = mUploadedForm.getAuthorizednumber();

        mCarOwnerShip = mUploadedForm.getCarownership();

        mCitizenship = mUploadedForm.getCitizenship();

        mHomeAddress = mUploadedForm.getHomeaddress();

        mAuthorizedContactPerson = mUploadedForm.getAuthorizedcontactperson();

        mEmployer = mUploadedForm.getEmployer();


        Log.d(TAG, "email: " + mEmail + ", mIndustry: " + mIndustry + ", mOfficeIndustry: " + mOfficeIndustry + ", mOfficeTelephone: " + mOfficeTelephone
                + ", mCity: " + mCity + ", mSalutation: " + mSalutation + ", mGender: " + mGender
                + ", mBirthdate: " + mBirthdate + ", mHomeOwnership: " + mHomeOwnership + ", mHomeOwnershipYears: " + mHomeOwnershipYears + ", mHomeOwnershipSalary: " + mHomeOwnershipSalary
                + ",mMothersMaidenName: " + mMothersMaidenName + ", mLastName: " + mLastName + ", mFirstName: " + mFirstName + ", mCivilStatus: " + mCivilStatus
                + ",mYearsInCompany: " + mYearsInCompany + ",mOccupation: " + mOccupation + ",mOfficeCity: " + mOfficeCity + ",mTin: " + mTin + ",mSSS: " + mSSS
                + ",mAuthorizedRelation: " + mAuthorizedRelation + ",mAuthorizedNumber: " + mAuthorizedNumber + ",mCarOwnerShip: " + mCarOwnerShip + ",mCitizenship: " + mCitizenship
                + ",mHomeAddress: " + mHomeAddress + ",mAuthorizedContactPerson: " + mAuthorizedContactPerson + ", mEmployer: " + mEmployer
                + ", accessToken: " + mAccessToken
        );

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application, container, false);

        mEmailSelection = (Spinner) view.findViewById(R.id.email_selection);
        mEmailText = (EditText) view.findViewById(R.id.email);
        mEmailText.setText(mEmail);


        mIndustrySelection = (Spinner) view.findViewById(R.id.industry_selection);
        mIndustryText = (EditText) view.findViewById(R.id.industry);
        mIndustryText.setText(mIndustry);


        mOfficeIndustrySelection = (Spinner) view.findViewById(R.id.officeindustry_selection);
        mOfficeIndustryText = (EditText) view.findViewById(R.id.officeindustry);
        mOfficeIndustryText.setText(mOfficeIndustry);


        mOfficeTelephoneSelection = (Spinner) view.findViewById(R.id.officetelephone_selection);
        mOfficeTelephoneText = (EditText) view.findViewById(R.id.officetelephone);
        mOfficeTelephoneText.setText(mOfficeTelephone);

        mCitySelection = (Spinner) view.findViewById(R.id.city_selection);
        mCityText = (EditText) view.findViewById(R.id.city);
        mCityText.setText(mCity);

        mSalutationSelection = (Spinner) view.findViewById(R.id.salutation_selection);
        mSalutationText = (EditText) view.findViewById(R.id.salutation);
        mSalutationText.setText(mSalutation);

        mGenderSelection = (Spinner) view.findViewById(R.id.gender_selection);
        mGenderText = (EditText) view.findViewById(R.id.gender);
        mGenderText.setText(mGender);

        mBirthdateSelection = (Spinner) view.findViewById(R.id.birthdate_selection);
        mBirthdateText = (EditText) view.findViewById(R.id.birthdate);
        mBirthdateText.setText(mBirthdate);

        mHomeOwnershipSelection = (Spinner) view.findViewById(R.id.homeownership_selection);
        mHomeOwnershipText = (EditText) view.findViewById(R.id.homeownership);
        mHomeOwnershipText.setText(mHomeOwnership);


        mHomeOwnershipYearsSelection = (Spinner) view.findViewById(R.id.homeownershipyears_selection);
        mHomeOwnershipYearsText = (EditText) view.findViewById(R.id.homeownershipyears);
        mHomeOwnershipYearsText.setText(mHomeOwnershipYears);

        mHomeOwnershipSalarySelection = (Spinner) view.findViewById(R.id.homeownershipsalary_selection);
        mHomeOwnershipSalaryText = (EditText) view.findViewById(R.id.homeownershipsalary);
        mHomeOwnershipSalaryText.setText(mHomeOwnershipSalary);

        mMothersMaidenNameSelection = (Spinner) view.findViewById(R.id.mothersmaidenname_selection);
        mMothersMaidenNameText = (EditText) view.findViewById(R.id.mothersmaidenname);
        mMothersMaidenNameText.setText(mMothersMaidenName);

        mLastNameSelection = (Spinner) view.findViewById(R.id.lastname_selection);
        mLastNameText = (EditText) view.findViewById(R.id.lastname);
        mLastNameText.setText(mLastName);

        mFirstNameSelection = (Spinner) view.findViewById(R.id.firstname_selection);
        mFirstNameText = (EditText) view.findViewById(R.id.firstname);
        mFirstNameText.setText(mFirstName);

        mCivilStatusSelection = (Spinner) view.findViewById(R.id.civilstatus_selection);
        mCivilStatusText = (EditText) view.findViewById(R.id.civilstatus);
        mCivilStatusText.setText(mCivilStatus);

        mYearsInCompanySelection = (Spinner) view.findViewById(R.id.yearsincompany_selection);
        mYearsInCompanyText = (EditText) view.findViewById(R.id.yearsincompany);
        mYearsInCompanyText.setText(mYearsInCompany);

        mOccupationSelection = (Spinner) view.findViewById(R.id.occupation_selection);
        mOccupationText = (EditText) view.findViewById(R.id.occupation);
        mOccupationText.setText(mOccupation);

        mOfficeCitySelection = (Spinner) view.findViewById(R.id.officecity_selection);
        mOfficeCityText = (EditText) view.findViewById(R.id.officecity);
        mOfficeCityText.setText(mOfficeCity);

        mTinSelection = (Spinner) view.findViewById(R.id.tin_selection);
        mTinText = (EditText) view.findViewById(R.id.tin);
        mTinText.setText(mTin);

        mSSSSelection = (Spinner) view.findViewById(R.id.sss_selection);
        mSSSText = (EditText) view.findViewById(R.id.sss);
        mSSSText.setText(mSSS);

        mAuthorizedRelationSelection = (Spinner) view.findViewById(R.id.authorizedrelation_selection);
        mAuthorizedRelationText = (EditText) view.findViewById(R.id.authorizedrelation);
        mAuthorizedRelationText.setText(mAuthorizedRelation);

        mAuthorizedNumberSelection = (Spinner) view.findViewById(R.id.authorizednumber_selection);
        mAuthorizedNumberText = (EditText) view.findViewById(R.id.authorizednumber);
        mAuthorizedNumberText.setText(mAuthorizedNumber);

        mCarOwnerShipSelection = (Spinner) view.findViewById(R.id.carownership_selection);
        mCarOwnerShipText = (EditText) view.findViewById(R.id.carownership);
        mCarOwnerShipText.setText(mCarOwnerShip);

        mCitizenshipSelection = (Spinner) view.findViewById(R.id.citizenship_selection);
        mCitizenshipText = (EditText) view.findViewById(R.id.citizenship);
        mCitizenshipText.setText(mCitizenship);

        mHomeAddressSelection = (Spinner) view.findViewById(R.id.homeaddress_selection);
        mHomeAddressText = (EditText) view.findViewById(R.id.homeaddress);
        mHomeAddressText.setText(mHomeAddress);

        mAuthorizedContactPersonSelection = (Spinner) view.findViewById(R.id.authorizedcontactperson_selection);
        mAuthorizedContactPersonText = (EditText) view.findViewById(R.id.authorizedcontactperson);
        mAuthorizedContactPersonText.setText(mAuthorizedContactPerson);

        mEmployerSelection = (Spinner) view.findViewById(R.id.employer_selection);
        mEmployerText = (EditText) view.findViewById(R.id.employer);
        mEmployerText.setText(mEmployer);


        initInformationSheet();
        return view;
    }

    private void initInformationSheet(){
        ArrayAdapter<String> adapterProfileSelection = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.customer_application));
        mEmailSelection.setAdapter(adapterProfileSelection);
        mEmailSelection.setSelection(0);

        mIndustrySelection.setAdapter(adapterProfileSelection);
        mIndustrySelection.setSelection(1);

        mOfficeIndustrySelection.setAdapter(adapterProfileSelection);
        mOfficeIndustrySelection.setSelection(2);

        mOfficeTelephoneSelection.setAdapter(adapterProfileSelection);
        mOfficeTelephoneSelection.setSelection(3);

        mCitySelection.setAdapter(adapterProfileSelection);
        mCitySelection.setSelection(4);

        mSalutationSelection.setAdapter(adapterProfileSelection);
        mSalutationSelection.setSelection(5);

        mGenderSelection.setAdapter(adapterProfileSelection);
        mGenderSelection.setSelection(6);

        mBirthdateSelection.setAdapter(adapterProfileSelection);
        mBirthdateSelection.setSelection(7);

        mHomeOwnershipSelection.setAdapter(adapterProfileSelection);
        mHomeOwnershipSelection.setSelection(8);

        mHomeOwnershipYearsSelection.setAdapter(adapterProfileSelection);
        mHomeOwnershipYearsSelection.setSelection(9);

        mHomeOwnershipSalarySelection.setAdapter(adapterProfileSelection);
        mHomeOwnershipSalarySelection.setSelection(10);

        mMothersMaidenNameSelection.setAdapter(adapterProfileSelection);
        mMothersMaidenNameSelection.setSelection(11);

        mLastNameSelection.setAdapter(adapterProfileSelection);
        mLastNameSelection.setSelection(12);

        mFirstNameSelection.setAdapter(adapterProfileSelection);
        mFirstNameSelection.setSelection(13);

        mCivilStatusSelection.setAdapter(adapterProfileSelection);
        mCivilStatusSelection.setSelection(14);

        mYearsInCompanySelection.setAdapter(adapterProfileSelection);
        mYearsInCompanySelection.setSelection(15);

        mOccupationSelection.setAdapter(adapterProfileSelection);
        mOccupationSelection.setSelection(16);

        mOfficeCitySelection.setAdapter(adapterProfileSelection);
        mOfficeCitySelection.setSelection(17);

        mTinSelection.setAdapter(adapterProfileSelection);
        mTinSelection.setSelection(18);

        mSSSSelection.setAdapter(adapterProfileSelection);
        mSSSSelection.setSelection(19);

        mAuthorizedRelationSelection.setAdapter(adapterProfileSelection);
        mAuthorizedRelationSelection.setSelection(20);

        mAuthorizedNumberSelection.setAdapter(adapterProfileSelection);
        mAuthorizedNumberSelection.setSelection(21);

        mCarOwnerShipSelection.setAdapter(adapterProfileSelection);
        mCarOwnerShipSelection.setSelection(22);

        mCitizenshipSelection.setAdapter(adapterProfileSelection);
        mCitizenshipSelection.setSelection(23);

        mHomeAddressSelection.setAdapter(adapterProfileSelection);
        mHomeAddressSelection.setSelection(24);

        mAuthorizedContactPersonSelection.setAdapter(adapterProfileSelection);
        mAuthorizedContactPersonSelection.setSelection(25);

        mEmployerSelection.setAdapter(adapterProfileSelection);
        mEmployerSelection.setSelection(26);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.save:
                updateUploadedForms();
                break;
        }
        return true;
    }

    /**
     * The method to update the pldt forms
     */
    private void updateUploadedForms(){
        UploadedFormDTO uploadedFormDTO = new UploadedFormDTO();


        String email = mEmailText.getText().toString().trim();
        String industry = mIndustryText.getText().toString().trim();
        String officeindustry = mOfficeIndustryText.getText().toString().trim();
        String officeTelephone = mOfficeTelephoneText.getText().toString().trim();
        String city = mCityText.getText().toString().trim();
        String salutation = mSalutationText.getText().toString().trim();
        String gender = mGenderText.getText().toString().trim();
        String birthdate = mBirthdateText.getText().toString().trim();
        String homeOwnership = mHomeOwnershipText.getText().toString().trim();
        String homeOwnershipyears = mHomeOwnershipYearsText.getText().toString().trim();
        String homeOwnershipsalary = mHomeOwnershipSalaryText.getText().toString().trim();
        String mothersMaidenName = mMothersMaidenNameText.getText().toString().trim();
        String lastName =  mLastNameText.getText().toString().trim();
        String firstName = mFirstNameText.getText().toString().trim();
        String civilStatus = mCivilStatusText.getText().toString().trim();
        String yearsInCompany = mYearsInCompanyText.getText().toString().trim();
        String occupation = mOccupationText.getText().toString().trim();
        String officeCity = mOfficeCityText.getText().toString().trim();
        String tin = mTinText.getText().toString().trim();
        String sss = mSSSText.getText().toString().trim();
        String authorizedRelation = mAuthorizedRelationText.getText().toString().trim();
        String authorizedNumber = mAuthorizedNumberText.getText().toString().trim();
        String carOwnerShip = mCarOwnerShipText.getText().toString().trim();
        String citizenShip = mCitizenshipText.getText().toString().trim();
        String homeAddress = mHomeAddressText.getText().toString().trim();
        String authorizedContactPerson = mAuthorizedContactPersonText.getText().toString().trim();
        String employer = mEmployerText.getText().toString().trim();

        uploadedFormDTO.setId(mId);
        uploadedFormDTO.setEmail(email);
        uploadedFormDTO.setIndustry(industry);
        uploadedFormDTO.setOfficeIndustry(officeindustry);
        uploadedFormDTO.setOfficeTelephone(officeTelephone);
        uploadedFormDTO.setCity(city);

        uploadedFormDTO.setBirthDate(birthdate);

        uploadedFormDTO.setMothersmaidenname(mothersMaidenName);
        uploadedFormDTO.setLastname(lastName);
        uploadedFormDTO.setFirstname(firstName);
        uploadedFormDTO.setCivilstatus(civilStatus);
        uploadedFormDTO.setYearsincompany(yearsInCompany);
        uploadedFormDTO.setOccupation(occupation);
        uploadedFormDTO.setOfficecity(officeCity);
        uploadedFormDTO.setTin(tin);
        uploadedFormDTO.setSss(sss);
        uploadedFormDTO.setAuthorizedrelation(authorizedRelation);
        uploadedFormDTO.setAuthorizednumber(authorizedNumber);
        uploadedFormDTO.setCarownership(carOwnerShip);
        uploadedFormDTO.setCitizenship(citizenShip);
        uploadedFormDTO.setHomeaddress(homeAddress);
        uploadedFormDTO.setAuthorizedcontactperson(authorizedContactPerson);
        uploadedFormDTO.setEmployer(employer);

        new UpdateApplicationValidationTask(uploadedFormDTO).execute(getApplicationValidation());

    }

    /**
     * The method for create url
     *
     * @return
     */
    private String getApplicationValidation() {
        return AppApi.URL_NAME + AppApi.APPLICATION_VALIDATION;
    }

    private class UpdateApplicationValidationTask extends AsyncTask<String, Void, UploadedFormDTO> {
        UploadedFormDTO uploadedFormDTO;

        public UpdateApplicationValidationTask(UploadedFormDTO uploadedFormDTO) {
            this.uploadedFormDTO = uploadedFormDTO;
        }

        @Override
        protected UploadedFormDTO doInBackground(String... url) {
            Log.i(TAG, "URL: " + url[0] + ", email: " + uploadedFormDTO.getEmail());
            return new UpdateApplicationValidationPostFetchr().fetchItems(url[0], uploadedFormDTO, mAccessToken);


        }

        @Override
        protected void onPostExecute(UploadedFormDTO uploadedFormDTO) {
            Log.i(TAG, "onPostExecute uploadedFormDTO: " + uploadedFormDTO);

            if (getActivity() == null)
                return;

            Log.d(TAG, "uploadedFormDTO id " + uploadedFormDTO.getId());

            int id = uploadedFormDTO.getId();

            if (id > 0){
                // set the message as complete validation
                showAssistant("validation confirmed");
            }else{
                showAssistant(null);
            }

            getActivity().finish();
        }
    }

    private void showAssistant(String result){
        Log.i(TAG, "result: " + result);
        Intent i = new Intent();
        i.putExtra(EXTRA_VALIDATION_RESULT, result);
        getActivity().setResult(Activity.RESULT_OK, i);
    }


}
