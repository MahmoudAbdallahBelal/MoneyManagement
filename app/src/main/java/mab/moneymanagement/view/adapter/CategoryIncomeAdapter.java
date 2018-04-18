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
 * Created by Gihan on 3/20/2018.
 */

public class CategoryIncomeAdapter extends BaseAdapter {
    private List<Category> mList;
    private Context context;

    public CategoryIncomeAdapter(Context context, List<Category> mList) {
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
        int tar = mList.get(position).getMoney();
        target.setText(tar + "");


        return v;

    }

    private int getIcomImage(String name) {
        if (name.equals(context.getString(R.string.food))) {
            return R.drawable.food;
        } else if (name.equals(context.getString(R.string.home))) {
            return R.drawable.house;
        } else if (name.equals(context.getString(R.string.personal))) {
            return R.drawable.personal;
        } else if (name.equals(context.getString(R.string.salary))) {
            return R.drawable.salalry;
        } else if (name.equals(context.getString(R.string.saving))) {
            return R.drawable.saving;
        } else if (name.equals(context.getString(R.string.shopping))) {
            return R.drawable.shopping;
        } else if (name.equals(context.getString(R.string.child))) {
            return R.drawable.child;
        } else if (name.equals(context.getString(R.string.car))) {
            return R.drawable.car;
        } else if (name.equals(context.getString(R.string.kast))) {
            return R.drawable.kast;
        } else
            return R.drawable.credit;
    }

}
