package mab.moneymanagement.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import mab.moneymanagement.R;
import mab.moneymanagement.view.activity.AllItemCategory;
import mab.moneymanagement.view.activity.DetailItemActivity;
import mab.moneymanagement.view.adapter.CategoryExpenseAdapter;
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.model.Item;

public class CategoryFragment extends Fragment {

    ImageView addIncome, addExpense;

    CategoryExpenseAdapter adapter;
    GridView mList;
    GridView expenceList;
    ArrayList<Category> expenseData = new ArrayList<>();
    ArrayList<Category> incomeData = new ArrayList<>();

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

        //List Expense  -----------
        mList = (GridView) v.findViewById(R.id.category_expense_list);

        expenseData.add(new Category("food", 2000.0, R.drawable.home));
        expenseData.add(new Category("food", 2000.0, R.drawable.home));
        expenseData.add(new Category("food", 2000.0, R.drawable.home));
        expenseData.add(new Category("food", 2000.0, R.drawable.home));
        expenseData.add(new Category("food", 2000.0, R.drawable.home));
        expenseData.add(new Category("food", 2000.0, R.drawable.home));
        expenseData.add(new Category("food", 2000.0, R.drawable.home));

        adapter = new CategoryExpenseAdapter(getContext(), expenseData);
        mList.setAdapter(adapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = expenseData.get(position);
                Intent detailIntent = new Intent(getActivity(), AllItemCategory.class);
                detailIntent.putExtra("categoryData", category);
                startActivity(detailIntent);

            }
        });

        //List Expense  ------------------------------------------------------------
        expenceList = (GridView) v.findViewById(R.id.category_income_list);

        incomeData.add(new Category("Cash", 4000.0, R.drawable.money));
        incomeData.add(new Category("Cash", 4000.0, R.drawable.money));

        adapter = new CategoryExpenseAdapter(getContext(), incomeData);
        expenceList.setAdapter(adapter);

        expenceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = incomeData.get(position);
                Intent detailIntent = new Intent(getActivity(), AllItemCategory.class);
                detailIntent.putExtra("categoryData", category);
                startActivity(detailIntent);

            }
        });


        //-------------------------
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
