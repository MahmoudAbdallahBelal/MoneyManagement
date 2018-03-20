package mab.moneymanagement.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import mab.moneymanagement.R;
import mab.moneymanagement.view.activity.DetailItemActivity;
import mab.moneymanagement.view.adapter.ExpectedAdapter;
import mab.moneymanagement.view.adapter.MainItemAdapter;
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.model.Item;


public class ExpectedFragment extends Fragment {
    ExpectedAdapter adapter;
    ListView mList;
    ArrayList<Category> data = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_expected, container, false);

        mList = v.findViewById(R.id.expected_list);
//
//        data.add(new Category("food", "expense", 2000.0, 1500.0));
//        data.add(new Category("food", "expense", 2000.0, 1500.0));
//        data.add(new Category("food", "expense", 2000.0, 1500.0));
//        data.add(new Category("food", "expense", 2000.0, 1500.0));
//        data.add(new Category("food", "expense", 2000.0, 1500.0));


        adapter = new ExpectedAdapter(getContext(), data);
        mList.setAdapter(adapter);


        return v;
    }


}
