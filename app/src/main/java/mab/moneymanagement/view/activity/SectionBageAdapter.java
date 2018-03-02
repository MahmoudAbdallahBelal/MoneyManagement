package mab.moneymanagement.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mab.moneymanagement.view.fragment.CalenderTapFragment;
import mab.moneymanagement.view.fragment.DailyFragment;
import mab.moneymanagement.view.fragment.MonthlyFragment;
import mab.moneymanagement.view.fragment.WeeklyFragment;


class SectionBageAdapter extends FragmentPagerAdapter {


    public SectionBageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DailyFragment dailyFragment = new DailyFragment();
                return dailyFragment;
            case 1:
                CalenderTapFragment calenderTapFragment = new CalenderTapFragment();
                return calenderTapFragment;
            case 2:
                WeeklyFragment weeklyFragment = new WeeklyFragment();
                return weeklyFragment;
            case 3:
                MonthlyFragment monthlyFragment = new MonthlyFragment();
                return monthlyFragment;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    public CharSequence getPageTitle(int postion) {

        switch (postion) {
            case 0:
                return "Daily";
            case 1:
                return "اCalender";
            case 2:
                return "Weekly";
            case 3:
                return "Monthly";
            default:
                return null;
        }
    }
}
