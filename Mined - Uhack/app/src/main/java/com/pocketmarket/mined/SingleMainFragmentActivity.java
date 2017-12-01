package com.pocketmarket.mined;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.pocketmarket.mined.view.GlobalMenuView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pocketmarket.mined.R.id.drawerLayout;

/**
 * Created by mark on 12/1/17.
 */

public abstract class SingleMainFragmentActivity extends AppCompatActivity
        implements GlobalMenuView.OnHeaderClickListener, AdapterView.OnItemClickListener {

    protected abstract Fragment createFragment();

    private final static String TAG = "SingleMainFragmentActivity";
    public Toolbar mToolbar;


    @BindView(drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.navigation)
    NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mined);
        ButterKnife.bind(this);

        // Disable the Animation
        getWindow().setWindowAnimations(0);

        // set the topbar for material design
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.icon_burger_menu);

        setupDrawer();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);

        if (fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .replace(R.id.mainContent, fragment)
                    .commit();
        }

    }

    private void setupDrawer() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Set on menu item selection listener
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void onGlobalMenuHeaderClick(final View v, final int channelId) {
        mDrawerLayout.closeDrawer(Gravity.LEFT);

//        int id = v.getId();

    }

    /**
     *
     */
    public void closeDrawerOnly(){
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
