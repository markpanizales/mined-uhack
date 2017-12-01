package com.pocketmarket.mined.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.pocketmarket.mined.SingleFragmentActivity;
import com.pocketmarket.mined.fragments.ApplicationFragment;

/**
 * Created by mark on 12/1/17.
 */

public class ApplicationActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {

        Bundle bundle = getIntent().getExtras();

        ApplicationFragment fragment = new ApplicationFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

}
