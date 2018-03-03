package mab.moneymanagement.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;

import mab.moneymanagement.R;
import mab.moneymanagement.view.model.Item;


public class DetailItem extends Fragment {

    EditText name,price,note;
    Spinner categorySpinner,paymentSpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_detail_item, container, false);

        name=(EditText)v.findViewById(R.id.item_detail_et_item_name);
        price=(EditText)v.findViewById(R.id.item_detail_price);
        note=(EditText)v.findViewById(R.id.item_detail_et_note);
        categorySpinner=(Spinner)v.findViewById(R.id.item_detail_category_spiner);
        paymentSpinner=(Spinner)v.findViewById(R.id.item_detail_payment_spiner);


        //------spinner for category ----------
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.category,
                android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //------spinner for currency ----------
        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.payment,
                android.R.layout.simple_spinner_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter);


        Item item= (Item) getActivity().getIntent().getSerializableExtra("item");

        name.setText(item.getName());
        price.setText(item.getPrice().toString());
        note.setText(item.getNote());








        return v;
    }





}
