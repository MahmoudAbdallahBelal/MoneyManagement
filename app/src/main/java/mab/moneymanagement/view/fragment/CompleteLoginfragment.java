package mab.moneymanagement.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import mab.moneymanagement.R;
import mab.moneymanagement.view.activity.CompleteLoginActivity;
import mab.moneymanagement.view.activity.Main2Activity;

public class CompleteLoginfragment extends Fragment {
    EditText etName, etSalary, etStartMonth;
    Button btnNext;
    Spinner currncySpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_complete_loginfragment, container, false);

        etName = (EditText) v.findViewById(R.id.complete_login_et_name);
        etSalary = (EditText) v.findViewById(R.id.complete_login_et_salary);
        etStartMonth = (EditText) v.findViewById(R.id.complete_login_et_start_month);
        btnNext = (Button) v.findViewById(R.id.complete_login_btn_next);

        //------spinner for category ----------
        Spinner currencySpinner = v.findViewById(R.id.complete_login_spinner_select_currency);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.currency,
                android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(categoryAdapter);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), Main2Activity.class);
                startActivity(mainIntent);

            }
        });

        return v;
    }

}
