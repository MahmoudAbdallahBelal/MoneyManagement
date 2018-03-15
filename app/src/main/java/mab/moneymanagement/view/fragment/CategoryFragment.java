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
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.activity.AllItemCategory;
import mab.moneymanagement.view.activity.DetailItemActivity;
import mab.moneymanagement.view.activity.Main2Activity;
import mab.moneymanagement.view.adapter.CategoryExpenseAdapter;
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.model.Item;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class CategoryFragment extends Fragment {

    ImageView addIncome, addExpense;

    CategoryExpenseAdapter adapter;
    GridView mList;
    GridView expenceList;
    ArrayList<Category> expenseData = new ArrayList<>();
    ArrayList<Category> incomeData = new ArrayList<>();

    URL url = new URL();

    String incomeCategoryUrl = url.PATH + url.CATEGORY_INCOME;
    MaterialDialog.Builder loginDaolog;
    SharedPreference shar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        addIncome = (ImageView) v.findViewById(R.id.add_income_image);
        addExpense = (ImageView) v.findViewById(R.id.addd_expense_image);

        loginDaolog = new MaterialDialog.Builder(getContext());
        shar = new SharedPreference();

        getAllCategory();

        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  dialogAddIncome();
                getAllCategory();

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


    private void getAllCategory() {

        final JSONObject loadObject = new JSONObject();
        try {
            loadObject.put("Authorization", shar.getValue(getContext()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, incomeCategoryUrl, loadObject,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                        for (int i = 0; i < response.length(); i++) {

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG);

                    }
                });


//        // Initialize a new JsonObjectRequest instance
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, incomeCategoryUrl, loadObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//
//                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
//                            String message = response.getString("RequstDetails");
//
//                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            loginDaolog.build().dismiss();
//
//                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG);
//
//                        }
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Do something when error occurred
//                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG);
//
//                    }
//                });

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonArrayRequest);

    }


}
