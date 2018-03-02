package mab.moneymanagement.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import mab.moneymanagement.R;


public class ChartFragment extends Fragment {


    BarChart barrChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chart, container, false);


        barrChart = (BarChart) v.findViewById(R.id.bar_chart);


        barrChart.setDrawBarShadow(false);
        barrChart.setDrawValueAboveBar(true);
        barrChart.setMaxVisibleValueCount(70);
        barrChart.setPinchZoom(false);
        barrChart.setDrawGridBackground(true);


        ArrayList<BarEntry> barEntry = new ArrayList<>();
        barEntry.add(new BarEntry(1, 100f));
        barEntry.add(new BarEntry(2, 30f));

        BarDataSet barDataSet = new BarDataSet(barEntry, "");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(.9f);
        barrChart.setData(data);


        return v;

    }


}
