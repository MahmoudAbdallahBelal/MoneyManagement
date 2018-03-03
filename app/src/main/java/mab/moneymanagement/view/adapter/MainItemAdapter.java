package mab.moneymanagement.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mab.moneymanagement.R;
import mab.moneymanagement.view.model.Item;

/**
 * Created by Gihan on 3/3/2018.
 */

public class MainItemAdapter extends BaseAdapter {

    private List<Item> mList;
    private Context context;

    public MainItemAdapter(Context context, List<Item> mList) {
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
        View v = View.inflate(context, R.layout.row_item_main_activity, null);

        TextView name = (TextView) v.findViewById(R.id.row_item_name);
        TextView price = (TextView) v.findViewById(R.id.row_item_price);
        TextView payment = (TextView) v.findViewById(R.id.row_item_payment);
        TextView note = (TextView) v.findViewById(R.id.row_item_note);

        name.setText(mList.get(position).getName());
        price.setText(mList.get(position).getPrice().toString());
        payment.setText(mList.get(position).getPayment());
        note.setText(mList.get(position).getNote());



        return v;

    }
}
