package mab.moneymanagement.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mab.moneymanagement.R;
import mab.moneymanagement.view.model.ExpectedData;

/**
 * Created by Gihan on 3/3/2018.
 */

public class ExpectedAdapter extends BaseAdapter {
    private List<ExpectedData> mList;
    private Context context;

    public ExpectedAdapter(Context context, List<ExpectedData> mList) {
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
        View v = View.inflate(context, R.layout.row_expected, null);

        TextView target = v.findViewById(R.id.row_expected_tv_target);
        TextView percentage = v.findViewById(R.id.row_expected_tv_percentage);
        TextView expense = v.findViewById(R.id.row_expected_tv_expense);
        TextView name = v.findViewById(R.id.row_expected_catgory_name);


        name.setText(mList.get(position).getName());


        int x1 = mList.get(position).getBudget();//target
        int x2 = mList.get(position).getMoney();//expense
        if (x1 == 0) {
            percentage.setText("0:0");

        } else {
            int percent = ((x1 - x2) / x1) * 100;
            percentage.setText(percent + "");

        }

        expense.setText(x2 + "");
        target.setText(x1 + "");


        return v;

    }
}
