package mab.moneymanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import mab.moneymanagement.R;
import mab.moneymanagement.view.activity.DetailItemActivity;
import mab.moneymanagement.view.adapter.MainItemAdapter;
import mab.moneymanagement.view.model.Item;


public class DailyFragment extends Fragment {

    MainItemAdapter adapter;
    ListView mList;
    ArrayList<Item> data = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_daily, container, false);

        mList = (ListView) v.findViewById(R.id.daily_fragment_list);

        data.add(new Item("Rice", 12.5, "Price is cheap", "Food", "cash", "12/2/2015"));
        data.add(new Item("Rice", 12.5, "Price is cheap", "Food", "cash", "12/2/2015"));
        data.add(new Item("Rice", 12.5, "Price is cheap", "Food", "cash", "12/2/2015"));
        data.add(new Item("Rice", 12.5, "Price is cheap", "Food", "cash", "12/2/2015"));
        data.add(new Item("Rice", 12.5, "Price is cheap", "Food", "cash", "12/2/2015"));

        adapter = new MainItemAdapter(getContext(), data);
        mList.setAdapter(adapter);


        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = data.get(position);
                Intent detailIntent = new Intent(getActivity(), DetailItemActivity.class);
                detailIntent.putExtra("item", item);
                startActivity(detailIntent);

            }
        });


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event

}
