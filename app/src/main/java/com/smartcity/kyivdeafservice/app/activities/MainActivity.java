package com.smartcity.kyivdeafservice.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartcity.kyivdeafservice.app.R;
import com.smartcity.kyivdeafservice.app.customViews.ScrimInsetsFrameLayout;
import com.smartcity.kyivdeafservice.app.fragments.ColorFragment;
import com.smartcity.kyivdeafservice.app.managers.ManagerTypeface;
import com.smartcity.kyivdeafservice.app.utils.UtilsDevice;
import com.smartcity.kyivdeafservice.app.utils.UtilsMiscellaneous;

/**
 * Main class hosting the navigation drawer
 *
 * @author Sotti https://plus.google.com/+PabloCostaTirado/about
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static double sNAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO = 9d / 16d;

    private Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;
    private FrameLayout mFlAccountView;
    private LinearLayout mLlRootView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;

    private FrameLayout mFlCalls;
    private FrameLayout mFlInterpreter;
    private FrameLayout mFlNews;
    private FrameLayout mFlJkh;
    private FrameLayout mFlEmergency;
    private FrameLayout mFlTaxi;
    private FrameLayout mFlSettings;
    private FrameLayout mFlAbout;

    private TextView mTvAccountDisplayName;
    private TextView mTvCalls;
    private TextView mTvInterpreter;
    private TextView mTvNews;
    private TextView mTvJkh;
    private TextView mTvEmergency;
    private TextView mTvTaxi;
    private TextView mTvSettings;
    private TextView mTvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        initViews();
        setTypefaces();

        initialise();
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    /**
     * Bind, create and set up the resources
     */
    private void initialise() {
        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle
                (
                        this,
                        mDrawerLayout,
                        mToolbar,
                        R.string.navigation_drawer_opened,
                        R.string.navigation_drawer_closed
                ) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Disables the burger/arrow animation by default
                super.onDrawerSlide(drawerView, 0);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mActionBarDrawerToggle.syncState();

        // Navigation Drawer layout width
        int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);

        // Account section height
        mFlAccountView.getLayoutParams().height = (int) (mScrimInsetsFrameLayout.getLayoutParams().width
                * sNAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO);

        setListeners();

        // Set the first item as selected for the first time
        getSupportActionBar().setTitle(R.string.toolbar_title_home);
        mFlCalls.setSelected(true);

        // Create the first fragment to be shown
        Bundle bundle = new Bundle();
        bundle.putInt(ColorFragment.sARGUMENT_COLOR, R.color.blue_500);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_activity_content_frame, ColorFragment.newInstance(bundle))
                .commit();
    }

    private void initViews() {
        // Layout resources
        mFlAccountView = (FrameLayout) findViewById(R.id.nd_fl_account_view);
        mLlRootView = (LinearLayout) findViewById(R.id.nd_ll_root_view);

        mFlCalls = (FrameLayout) findViewById(R.id.nd_fl_calls);
        mFlInterpreter = (FrameLayout) findViewById(R.id.nd_fl_interpreter);
        mFlNews = (FrameLayout) findViewById(R.id.nd_fl_news);
        mFlJkh = (FrameLayout) findViewById(R.id.nd_fl_jkh);
        mFlEmergency = (FrameLayout) findViewById(R.id.nd_fl_emergency);
        mFlTaxi = (FrameLayout) findViewById(R.id.nd_fl_taxi);
        mFlSettings = (FrameLayout) findViewById(R.id.nd_fl_settings);
        mFlAbout = (FrameLayout) findViewById(R.id.nd_fl_about);

        mTvAccountDisplayName = (TextView) findViewById(R.id.nd_tv_account_name);

        mTvCalls = (TextView) findViewById(R.id.nd_tv_calls);
        mTvInterpreter = (TextView) findViewById(R.id.nd_tv_interpreter);
        mTvNews = (TextView) findViewById(R.id.nd_tv_news);
        mTvJkh = (TextView) findViewById(R.id.nd_tv_jkh);
        mTvEmergency = (TextView) findViewById(R.id.nd_tv_emergency);
        mTvTaxi = (TextView) findViewById(R.id.nd_tv_taxi);
        mTvSettings = (TextView) findViewById(R.id.nd_tv_settings);
        mTvAbout = (TextView) findViewById(R.id.nd_tv_about);
    }

    private void setTypefaces() {
        // Typefaces
        mTvAccountDisplayName.setTypeface(
                ManagerTypeface.getTypeface(MainActivity.this, R.string.typeface_roboto_medium));
        mTvCalls.setTypeface(
                ManagerTypeface.getTypeface(MainActivity.this, R.string.typeface_roboto_medium));
        mTvInterpreter.setTypeface(
                ManagerTypeface.getTypeface(MainActivity.this, R.string.typeface_roboto_medium));
        mTvNews.setTypeface(
                ManagerTypeface.getTypeface(MainActivity.this, R.string.typeface_roboto_medium));
        mTvJkh.setTypeface(
                ManagerTypeface.getTypeface(MainActivity.this, R.string.typeface_roboto_medium));
        mTvEmergency.setTypeface(
                ManagerTypeface.getTypeface(MainActivity.this, R.string.typeface_roboto_medium));
        mTvTaxi.setTypeface(
                ManagerTypeface.getTypeface(MainActivity.this, R.string.typeface_roboto_medium));
        mTvSettings.setTypeface(
                ManagerTypeface.getTypeface(MainActivity.this, R.string.typeface_roboto_medium));
        mTvAbout.setTypeface(
                ManagerTypeface.getTypeface(MainActivity.this, R.string.typeface_roboto_medium));
    }

    private void setListeners() {
        // Nav Drawer item click listener
        mFlAccountView.setOnClickListener(this);
        mFlCalls.setOnClickListener(this);
        mFlInterpreter.setOnClickListener(this);
        mFlNews.setOnClickListener(this);
        mFlJkh.setOnClickListener(this);
        mFlEmergency.setOnClickListener(this);
        mFlTaxi.setOnClickListener(this);
        mFlSettings.setOnClickListener(this);
        mFlAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.nd_fl_account_view) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);

            // If the user is signed in, go to the profile, otherwise show sign up / sign in
        } else {
            if (!view.isSelected()) {
                onRowPressed((FrameLayout) view);
                onRowSelected(view);
            } else {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        }
    }

    /**
     * Set up the rows when any is pressed
     *
     * @param pressedRow is the pressed row in the drawer
     */
    private void onRowPressed(FrameLayout pressedRow) {
        if (pressedRow.getTag() != getResources().getString(R.string.tag_nav_drawer_special_entry)) {
            for (int i = 0; i < mLlRootView.getChildCount(); i++) {
                View currentView = mLlRootView.getChildAt(i);

                boolean currentViewIsMainEntry = currentView.getTag() ==
                        getResources().getString(R.string.tag_nav_drawer_main_entry);

                if (currentViewIsMainEntry) {
                    if (currentView == pressedRow) {
                        currentView.setSelected(true);
                    } else {
                        currentView.setSelected(false);
                    }
                }
            }
        }

        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void onRowSelected(View view) {
        switch (view.getId()) {
            case R.id.nd_fl_calls:
                view.setSelected(true);
                showColorFragment();
                break;

            case R.id.nd_fl_interpreter:
                view.setSelected(true);
                showColorFragment();
                break;

            case R.id.nd_fl_news:
                view.setSelected(true);
                showColorFragment();
                break;

            case R.id.nd_fl_jkh:
                view.setSelected(true);
                showColorFragment();
                break;

            case R.id.nd_fl_emergency:
                view.setSelected(true);
                showColorFragment();
                break;

            case R.id.nd_fl_taxi:
                view.setSelected(true);
                showColorFragment();
                break;

            case R.id.nd_fl_settings:
                // Start intent to send an email
                startActivity(new Intent(view.getContext(), OtherActivity.class));
                break;

            case R.id.nd_fl_about:
                // Show about activity
                startActivity(new Intent(view.getContext(), OtherActivity.class));
                break;
        }
    }

    private void showColorFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.toolbar_title_explore));
        }

        Bundle bundle = new Bundle();
        bundle.putInt(ColorFragment.sARGUMENT_COLOR, R.color.amber_500);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_activity_content_frame, ColorFragment.newInstance(bundle))
                .commit();
    }
}
