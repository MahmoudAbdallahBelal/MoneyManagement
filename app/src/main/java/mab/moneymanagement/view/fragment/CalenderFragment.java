package mab.moneymanagement.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import mab.moneymanagement.R;


public class CalenderFragment extends Fragment {
    CalendarView calendarView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calender, container, false);
        calendarView = v.findViewById(R.id.calendar_fragmenttt);
        long selectedDate = calendarView.getDate(); // get selected date in milliseconds
        calendarView.setDate(selectedDate);

        return v;
    }


}
