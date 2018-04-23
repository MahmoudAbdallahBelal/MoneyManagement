package mab.moneymanagement.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import mab.moneymanagement.R;
import mab.moneymanagement.view.model.MonthStatics;

/**
 * Created by Gihan on 3/4/2018.
 */

public class MonthlystaticsAdapter extends BaseAdapter {

    private List<MonthStatics> mList;
    private Context context;
    int expen;
    int yy;

    int x1 = 0;
    int x2 = 0;
    public MonthlystaticsAdapter(Context context, List<MonthStatics> mList) {
        this.context = context;
        this.mList = mList;


    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.row_monthly_statics, null);

        TextView reset = v.findViewById(R.id.monthlystatic_reset);
        TextView expense = v.findViewById(R.id.monthlystatic_expense);
        TextView date = v.findViewById(R.id.monthly_statics_date);


        expen = mList.get(position).getMoney();
        yy = mList.get(position).getBudget() - expen;

//if (yy<0){
//    yy=0;
//}
        int sum = expen + yy;
        x1 = (expen / sum) * 100;
        x2 = (yy / sum) * 100;


        reset.setText(yy + "");
        expense.setText(expen + "");
        date.setText(getMonthName(mList.get(position).getMonth()) + " " + mList.get(position).getMonth() + " / " + mList.get(position).getYear());


        //----------chart --------
        PieChart pieChart = v.findViewById(R.id.pie_chart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(android.R.color.black);
        pieChart.setTransparentCircleRadius(31f);


        ArrayList<PieEntry> yEntryValue = new ArrayList<>();
        yEntryValue.add(new PieEntry((float) yy, context.getString(R.string.reset)));
        yEntryValue.add(new PieEntry((float) expen, context.getString(R.string.expense)));

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yEntryValue, "");
        //dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        //---------------


        return v;

    }

    private String getMonthName(int x) {
        String monthString;
        switch (x) {
            case 1:
                monthString = context.getString(R.string.month1);
                break;
            case 2:
                monthString = context.getString(R.string.month2);
                break;
            case 3:
                monthString = context.getString(R.string.month3);
                break;
            case 4:
                monthString = context.getString(R.string.month4);
                break;
            case 5:
                monthString = context.getString(R.string.month5);
                break;
            case 6:
                monthString = context.getString(R.string.month6);
                break;
            case 7:
                monthString = context.getString(R.string.month7);
                break;
            case 8:
                monthString = context.getString(R.string.month8);
                break;
            case 9:
                monthString = context.getString(R.string.month9);
                break;
            case 10:
                monthString = context.getString(R.string.month10);
                break;
            case 11:
                monthString = context.getString(R.string.month11);
                break;
            case 12:
                monthString = context.getString(R.string.month12);
                break;
            default:
                monthString = context.getString(R.string.month13);
                break;
        }
        return monthString;

    }
}
