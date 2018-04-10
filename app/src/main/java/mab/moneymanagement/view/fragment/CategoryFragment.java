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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.activity.AllItemCategory;
import mab.moneymanagement.view.adapter.CategoryExpenseAdapter;
import mab.moneymanagement.view.adapter.CategoryIncomeAdapter;
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class CategoryFragment extends Fragment {

    ImageView addIncome, addExpense;

    CategoryExpenseAdapter adapter;
    CategoryIncomeAdapter incomeAdapter;
    GridView mList;
    GridView incomeList;

    ArrayList<Category> expenseData = new ArrayList<>();
    ArrayList<Category> incomeData = new ArrayList<>();

    String selectedCategory;


    String incomeCategoryUrl = URL.PATH + URL.CATEGORY_INCOME;
    String expenseCategoryUrl = URL.PATH + URL.CATEGORY_EXPENSE;


    SharedPreference shar;

    String categoryName;
    int value;

    @Override
    public void onPause() {
        super.onPause();

        getExpenseCategory();
        getinComeseCategory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        addIncome = v.findViewById(R.id.add_income_image);
        addExpense = v.findViewById(R.id.addd_expense_image);

        shar = new SharedPreference();


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
        mList = v.findViewById(R.id.category_expense_list);
        getExpenseCategory();

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = expenseData.get(position);
                Intent detailIntent = new Intent(getActivity(), AllItemCategory.class);
                detailIntent.putExtra("kind", 1);
                detailIntent.putExtra("categoryData", category);

                startActivity(detailIntent);

            }
        });

        //List Income ------------------------------------------------------------
        incomeList = v.findViewById(R.id.category_income_list);
        getinComeseCategory();

        incomeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        final View expense_layout = inflater.inflate(R.layout.add_expense, null);

        final EditText expenseName = expense_layout.findViewById(R.id.add_expense_et_name);
        final EditText expensvalue = expense_layout.findViewById(R.id.add_expense_et_value);
        final ImageView iconImage = expense_layout.findViewById(R.id.add_expense_icon);


        //------spinner for icon ----------
        final Spinner iconSpinner = expense_layout.findViewById(R.id.spinner_icons_new_expence);
        final ArrayAdapter<CharSequence> iconAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.icon, android.R.layout.simple_spinner_item);
        iconAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iconSpinner.setAdapter(iconAdapter);

        iconSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                iconImage.setImageResource(getIcomImage(position));
                selectedCategory = iconSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        builder.setView(expense_layout);

        //SET BUTTON
        builder.setPositiveButton("Add  ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                categoryName = expenseName.getText().toString();
                String hh = expensvalue.getText().toString();
                if (categoryName.equals("") || hh.equals("")) {
                } else {
                    value = Integer.parseInt(hh);
                    addCategoryExpense(categoryName, value, selectedCategory);
                }
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
        final View expense_layout = inflater.inflate(R.layout.add_income, null);

        final EditText expenseName = expense_layout.findViewById(R.id.add_income_et_name);
        final EditText expensvalue = expense_layout.findViewById(R.id.add_income_et_value);
        final ImageView iconImage = expense_layout.findViewById(R.id.add_income_icon);


        //------spinner for day ----------
        final Spinner iconSpinner = expense_layout.findViewById(R.id.spinner_icons_new_income);
        final ArrayAdapter<CharSequence> iconAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.icon, android.R.layout.simple_spinner_item);
        iconAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iconSpinner.setAdapter(iconAdapter);

        iconSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                iconImage.setImageResource(getIcomImage(position));
                selectedCategory = iconSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        builder.setView(expense_layout);

        //SET BUTTON
        builder.setPositiveButton("Add  ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                categoryName = expenseName.getText().toString();
                String hh = expensvalue.getText().toString();
                if (categoryName.equals("") || hh.equals("")) {
                } else {
                    value = Integer.parseInt(hh);
                    addCategoryIncome(categoryName, value, selectedCategory);
                }
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

    private void addCategoryIncome(final String categoryName, final int value, final String selectedCategory) {


        final JSONObject regsterObject = new JSONObject();
        try {


            regsterObject.put("Name", categoryName);
            regsterObject.put("Icon", selectedCategory);
            regsterObject.put("Price", value);
            regsterObject.put("Id", 0);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, incomeCategoryUrl, regsterObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");

                            incomeData.clear();
                            getinComeseCategory();

                            Toast.makeText(getContext(), getString(R.string.add_done), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            addCategoryIncome(categoryName, value, selectedCategory);
                            // Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

                        addCategoryIncome(categoryName, value, selectedCategory);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getContext()));
                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

    }

    private void addCategoryExpense(String name, final int value, String icon) {


        final JSONObject regsterObject = new JSONObject();
        try {


            regsterObject.put("Name", name);
            regsterObject.put("Icon", icon);
            regsterObject.put("Price", value);
            regsterObject.put("Id", 0);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, expenseCategoryUrl, regsterObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            Toast.makeText(getContext(), getString(R.string.add_done), Toast.LENGTH_LONG).show();

                            // Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

                            expenseData.clear();
                            getExpenseCategory();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            addCategoryExpense(categoryName, value, selectedCategory);

                            //Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        //  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

                        addCategoryExpense(categoryName, value, selectedCategory);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getContext()));
                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

    }

    private int getIcomImage(int name) {
        if (name == 0) {
            return R.drawable.food;
        } else if (name == 1) {
            return R.drawable.house;


        } else if (name == 2) {
            return R.drawable.personal;

        } else if (name == 3) {
            return R.drawable.salalry;

        } else if (name == 4) {
            return R.drawable.saving;

        } else if (name == 5) {
            return R.drawable.shopping;

        } else if (name == 6) {
            return R.drawable.child;

        } else if (name == 7) {
            return R.drawable.car;

        } else if (name == 8) {
            return R.drawable.kast;

        } else
            return R.drawable.credit;


    }

    //------------------------------------------------------------------------------------
    private void getExpenseCategory() {


        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, expenseCategoryUrl, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            // Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            JSONArray arr = response.getJSONArray("data");


                            expenseData.clear();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObject = arr.getJSONObject(i);
                                Category category = new Category(
                                        jsonObject.getInt("Id"),
                                        jsonObject.getString("Name"),
                                        jsonObject.getString("Icon"),
                                        jsonObject.getInt("Money"),
                                        jsonObject.getInt("Budget"),
                                        jsonObject.getString("CreateDate"),
                                        "expense"

                                );
                                expenseData.add(category);

                            }
                            adapter = new CategoryExpenseAdapter(getContext(), expenseData);
                            mList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            getExpenseCategory();


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getExpenseCategory();


                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getContext()));

                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

    }

    private void getinComeseCategory() {


        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, incomeCategoryUrl, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            // Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            JSONArray arr = response.getJSONArray("data");

                            incomeData.clear();

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObject = arr.getJSONObject(i);
                                Category category = new Category(
                                        jsonObject.getInt("Id"),
                                        jsonObject.getString("Name"),
                                        jsonObject.getString("Icon"),
                                        jsonObject.getInt("Money"),
                                        jsonObject.getInt("Budget"),
                                        jsonObject.getString("CreateDate"),
                                        "income"

                                );
                                incomeData.add(category);


                            }
                            incomeAdapter = new CategoryIncomeAdapter(getContext(), incomeData);
                            incomeList.setAdapter(incomeAdapter);
                            incomeAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            getinComeseCategory();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getinComeseCategory();


                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getContext()));
                String hh = shar.getValue(getActivity());

                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

    }

}





