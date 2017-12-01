package com.pocketmarket.mined.activity;

import android.support.v4.app.Fragment;

import com.pocketmarket.mined.SingleFragmentActivity;
import com.pocketmarket.mined.fragments.FeedsFragment;

/**
 * Created by mark on 12/1/17.
 */

public class FeedsActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new FeedsFragment();
    }
}
