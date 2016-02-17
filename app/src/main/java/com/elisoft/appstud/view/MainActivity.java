package com.elisoft.appstud.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.elisoft.appstud.R;
import com.elisoft.appstud.view.bookmarks.BookmarksFragment;
import com.elisoft.appstud.view.profile.ProfileFragment;
import com.elisoft.appstud.view.rockstars.RockStarsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final int TAB_NUMBER = 3;
    private final int FRAGMENT_ROCKSTARS_POSITION = 0;
    private final int FRAGMENT_BOOKMARKS_POSITION = 1;
    private final int FRAGMENT_PROFILE_POSITION = 2;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Bind(R.id.header_bar)
    HeaderBar headerBar;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @Bind(R.id.container)
    ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /**
                 * Update header title when page changed
                 */
                if (position == FRAGMENT_BOOKMARKS_POSITION) {
                    headerBar.setHeaderTitle(getString(R.string.bookmarks));
                } else if (position == FRAGMENT_ROCKSTARS_POSITION) {
                    headerBar.setHeaderTitle(getString(R.string.rockstars));
                } else if (position == FRAGMENT_PROFILE_POSITION) {
                    headerBar.setHeaderTitle(getString(R.string.profile));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Link tablayout to view pager
        tabLayout.setupWithViewPager(viewPager);

        // Custom tabs' layouts
        setupCustomTabs();
    }

    /**
     * Setup customized tabs (with text & icon)
     */
    private void setupCustomTabs() {
        TextView rockstarsTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        rockstarsTab.setText(getString(R.string.rockstars));
        rockstarsTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_rockstars, 0, 0);
        tabLayout.getTabAt(0).setCustomView(rockstarsTab);

        TextView bookmarksTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        bookmarksTab.setText(getString(R.string.bookmarks));
        bookmarksTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_bookmarks, 0, 0);
        tabLayout.getTabAt(1).setCustomView(bookmarksTab);

        TextView profileTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        profileTab.setText(getString(R.string.profile));
        profileTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_profile, 0, 0);
        tabLayout.getTabAt(2).setCustomView(profileTab);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == FRAGMENT_BOOKMARKS_POSITION) {
                return BookmarksFragment.getInstance();
            } else if (position == FRAGMENT_ROCKSTARS_POSITION) {
                return RockStarsFragment.getInstance();
            }
            return ProfileFragment.getInstance();
        }

        @Override
        public int getCount() {
            // Return number of tab
            return TAB_NUMBER;
        }
    }
}
