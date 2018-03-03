package mab.moneymanagement.view.fragment;



import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;


import mab.moneymanagement.R;


public class SettingFragment extends Fragment {


    CheckedTextView tvBudget;
    TextView tvLanguage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        tvBudget=v.findViewById(R.id.setting_check_tv_budget);
        tvLanguage=v.findViewById(R.id.setting_tv_select_language);









        //-------------
        tvBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBudgetDialog();

            }
        });

        tvLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//                FragmentManager fm = getChildFragmentManager();
//                DialogAddItemFragment dialogFragment = new DialogAddItemFragment();
//                dialogFragment.show(fm, "");


            }
        });
        return v;
    }


    private void showBudgetDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View budget_layout = inflater.inflate(R.layout.budget, null);


        EditText budgetValue=budget_layout.findViewById(R.id.budget_et_value);
        Button ok=budget_layout.findViewById(R.id.budget_btn_ok);
        Button cancel=budget_layout.findViewById(R.id.budget_btn_cancel);


        final DialogInterface dialog=new DialogInterface() {
            @Override
            public void cancel() {

            }

            @Override
            public void dismiss() {

            }
        };
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        builder.setView(budget_layout);
//
//        //SET BUTTON
//        builder.setPositiveButton("Budeget ", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                dialog.dismiss();
//
//            }
//
//        });

//
//        builder.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//
//            }
//        });


        builder.show();
    }


}
