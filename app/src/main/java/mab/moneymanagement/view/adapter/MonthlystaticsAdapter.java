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
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.model.Item;

/**
 * Created by Gihan on 3/4/2018.
 */

public class MonthlystaticsAdapter extends BaseAdapter {

    private List<Category> mList;
    private Context context;

    public MonthlystaticsAdapter(Context context, List<Category> mList) {
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

        TextView reset = (TextView) v.findViewById(R.id.monthlystatic_reset);
        TextView expense = (TextView) v.findViewById(R.id.monthlystatic_expense);
        TextView date = (TextView) v.findViewById(R.id.monthly_statics_date);


        double expen = mList.get(position).getExpense();
        double tar = mList.get(position).getTarget();
        double res = tar - expen;

        reset.setText(res + "");
        expense.setText(expen + "");
        date.setText(mList.get(position).getDate());


        //----------chart --------
        PieChart pieChart = (PieChart) v.findViewById(R.id.pie_chart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(android.R.color.black);
        pieChart.setTransparentCircleRadius(31f);


        ArrayList<PieEntry> yEntryValue = new ArrayList<>();
        yEntryValue.add(new PieEntry((float) res, "Reset"));
        yEntryValue.add(new PieEntry((float) expen, "Expense"));

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yEntryValue, "");
        //dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
        //---------------


        return v;

    }
}
