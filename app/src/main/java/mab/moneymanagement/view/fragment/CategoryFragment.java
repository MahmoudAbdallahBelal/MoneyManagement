package mab.moneymanagement.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import mab.moneymanagement.R;

public class CategoryFragment extends Fragment {

    ImageView addIncome, addExpense;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        addIncome = (ImageView) v.findViewById(R.id.add_income_image);
        addExpense = (ImageView) v.findViewById(R.id.addd_expense_image);

        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddIncome();

            }
        });
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogAddExpense();
            }
        });


        return v;
    }

    private void dialogAddExpense() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View expense_layout = inflater.inflate(R.layout.add_expense, null);

        // EditText expenseName=expense_layout.findViewById(R.id.budget_et_value);
        builder.setView(expense_layout);

        //SET BUTTON
        builder.setPositiveButton("Add  ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }

        });


        builder.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        builder.show();
    }

    private void dialogAddIncome() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View income_layout = inflater.inflate(R.layout.add_income, null);

        // EditText expenseName=expense_layout.findViewById(R.id.budget_et_value);
        builder.setView(income_layout);

        //SET BUTTON
        builder.setPositiveButton("Add  ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }

        });


        builder.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        builder.show();
    }


}
