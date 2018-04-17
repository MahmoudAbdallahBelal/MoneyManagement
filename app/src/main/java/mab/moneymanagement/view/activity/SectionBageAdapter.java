package mab.moneymanagement.view.activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mab.moneymanagement.R;
import mab.moneymanagement.view.fragment.CalenderTapFragment;
import mab.moneymanagement.view.fragment.DailyFragment;
import mab.moneymanagement.view.fragment.MonthlyFragment;
import mab.moneymanagement.view.fragment.WeeklyFragment;


class SectionBageAdapter extends FragmentPagerAdapter {


    Context contextt;

    public SectionBageAdapter(FragmentManager fm, Context context) {
        super(fm);
        contextt = context;
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

        //  String yourstring =contextt.getString();

        switch (postion) {
            case 0:
                return contextt.getString(R.string.daily);
            case 1:
                return contextt.getString(R.string.claender);
            case 2:
                return contextt.getString(R.string.weekly);
            case 3:
                return contextt.getString(R.string.monthly);
            default:
                return null;
        }
    }
}
