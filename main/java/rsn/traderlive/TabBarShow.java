package rsn.traderlive;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class TabBarShow extends FragmentPagerAdapter {


    private static final String ARG_TAB_NAME = "section_name";

    public TabBarShow(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new TabBarPunter();
        Bundle args = new Bundle();


        if(position == 0) {
            fragment = new TabBarLive1();
            args.putString(ARG_TAB_NAME, "live1");
        }

        if(position == 1) {
            //args.putInt(ARG_SECTION_NAME, "punter");
            args.putString(ARG_TAB_NAME, "punter");
        }
        if(position == 2) {
            fragment = new TabBarMult();
            args.putString(ARG_TAB_NAME, "multipla");
        }

        if(position == 3) {
            //fragment = new TabBarInfo();
            //args.putString(ARG_TAB_NAME, "info");
            fragment = new TabBarMult2();
            args.putString(ARG_TAB_NAME, "multipla2");
        }


        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "LIVE";
            case 1:
                return "PUNTER";
            case 2:
                return "MULT1";
            case 3:
                return "MULT2";

        }
        return null;
    }
}

