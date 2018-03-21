package mab.moneymanagement.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import mab.moneymanagement.R;
import mab.moneymanagement.view.activity.MonthlyStatics;
import mab.moneymanagement.view.adapter.ExpectedAdapter;
import mab.moneymanagement.view.adapter.MonthlystaticsAdapter;
import mab.moneymanagement.view.model.Category;


public class MonthlystaticsFragment extends Fragment {

    MonthlystaticsAdapter adapter;
    ListView mList;
    ArrayList<Category> data = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_monthlystatics, container, false);

        mList = v.findViewById(R.id.monthly_statics_list);

        data.clear();


        adapter = new MonthlystaticsAdapter(getContext(), data);
        mList.setAdapter(adapter);


        return v;
    }

}
