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

        ImageView icon = (ImageView) v.findViewById(R.id.row_category_house_icon);

        TextView target = (TextView) v.findViewById(R.id.row_category_target_value);

        TextView name = (TextView) v.findViewById(R.id.row_category_category_name);


        name.setText(mList.get(position).getName());
        icon.setImageAlpha(mList.get(position).getIcon());
        double tar = mList.get(position).getTarget();
        target.setText(tar + "");


        return v;

    }
}
