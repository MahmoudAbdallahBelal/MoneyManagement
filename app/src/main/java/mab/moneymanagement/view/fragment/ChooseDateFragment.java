package mab.moneymanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;


import mab.moneymanagement.R;
import mab.moneymanagement.view.activity.AllItemChooseDate;


public class ChooseDateFragment extends Fragment {
    CalendarView calendarView;
    Button done;
    int dayy;
    int monthh;
    int yearr;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_choose_date, container, false);

        calendarView = v.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                yearr = year;
                dayy = dayOfMonth;
                monthh = month;

            }
        });
        done = v.findViewById(R.id.choose_date_done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayy == 0) {
                    Toast.makeText(getActivity(), getString(R.string.select_day), Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getActivity(), AllItemChooseDate.class);
                    intent.putExtra("day", dayy);
                    intent.putExtra("month", monthh + 1);
                    intent.putExtra("year", yearr);
                    startActivity(intent);
                }
            }
        });

        return v;
    }


}
