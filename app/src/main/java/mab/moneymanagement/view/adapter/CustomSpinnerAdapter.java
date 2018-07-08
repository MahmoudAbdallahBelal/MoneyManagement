package mab.moneymanagement.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mab.moneymanagement.R;
import mab.moneymanagement.view.model.Category;

/**
 * Created by Gihan on 3/20/2018.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<Category> {


    private List<Category> mList;
    private Context context;
    private int mResource;

    public CustomSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mList = objects;
        this.mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }


    private View createItemView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.spinner_row, parent, false);

        ImageView icon = v.findViewById(R.id.spinner_row_icon);
        TextView name = v.findViewById(R.id.spinner_row_name);
        name.setText(mList.get(position).getName());
        icon.setImageResource(getIcomImage(mList.get(position).getIcon()));


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
