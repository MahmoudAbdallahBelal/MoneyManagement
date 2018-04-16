package mab.moneymanagement.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mab.moneymanagement.R;
import mab.moneymanagement.view.model.Category;

/**
 * Created by Gihan on 3/4/2018.
 */

public class CategoryExpenseAdapter extends BaseAdapter {
    private List<Category> mList;
    private Context context;

    public CategoryExpenseAdapter(Context context, List<Category> mList) {
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
        View v = View.inflate(context, R.layout.row_category, null);

        ImageView icon = v.findViewById(R.id.row_category_house_icon);
        TextView target = v.findViewById(R.id.row_category_target_value);
        TextView name = v.findViewById(R.id.row_category_category_name);


        name.setText(mList.get(position).getName());
        icon.setImageResource(getIcomImage(mList.get(position).getIcon()));
        int tar = mList.get(position).getBudget();
        target.setText(tar + "");


        return v;

    }

    private int getIcomImage(String name) {
        if (name.equals("Food")) {
            return R.drawable.food;
        } else if (name.equals("Home")) {
            return R.drawable.house;
        } else if (name.equals("Personal")) {
            return R.drawable.personal;
        } else if (name.equals("Salary")) {
            return R.drawable.salalry;
        } else if (name.equals("saving")) {
            return R.drawable.saving;
        } else if (name.equals("Shopping")) {
            return R.drawable.shopping;
        } else if (name.equals("Child")) {
            return R.drawable.child;
        } else if (name.equals("Car")) {
            return R.drawable.car;
        } else if (name.equals("Kast")) {
            return R.drawable.kast;
        } else
            return R.drawable.credit;
    }

}
