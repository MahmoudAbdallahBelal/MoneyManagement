package mab.moneymanagement.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import mab.moneymanagement.R;
import mab.moneymanagement.view.model.Category;


public class CategoryDetailFragment extends Fragment {
    EditText name;
    EditText value;
    Spinner icon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //--get category data from intent to put in menu -------
        Category categoryData = (Category) getActivity().getIntent().getSerializableExtra("category");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category_detail, container, false);
        name=(EditText)v.findViewById(R.id.category_detail_name);
        value=(EditText)v.findViewById(R.id.category_detail_value);

        name.setText(categoryData.getName());
        double valu=categoryData.getTarget();
        value.setText(valu+"");


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event

}
