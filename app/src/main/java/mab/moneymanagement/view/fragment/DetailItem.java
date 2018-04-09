package mab.moneymanagement.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.adapter.CustomSpinnerAdapter;
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.model.Item;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;


public class DetailItem extends Fragment {

    EditText name, price, note;
    Button update, delete;
    Spinner categorySpinner, paymentSpinner;
    CustomSpinnerAdapter categoryAdapter;
    CustomSpinnerAdapter incomeAdapter;

    SharedPreference shar;

    String incomeCategoryUrl = URL.PATH + URL.CATEGORY_INCOME;
    String expenseCategoryUrl = URL.PATH + URL.CATEGORY_EXPENSE;


    ArrayList<Category> expenseData = new ArrayList<>();
    ArrayList<Category> incomeData = new ArrayList<>();
    String itemUrl = URL.PATH + URL.ITEM;
    String deleteUrl = URL.PATH + URL.DELETE_ITEM;


    int incomeId = -1;
    int expenseId = -1;

    Item item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_item, container, false);

        item = (Item) getActivity().getIntent().getSerializableExtra("item");


        name = v.findViewById(R.id.item_detail_et_item_name);
        price = v.findViewById(R.id.item_detail_price);
        note = v.findViewById(R.id.item_detail_et_note);
        update = v.findViewById(R.id.item_detail_update);
        delete = v.findViewById(R.id.item_detail_delete);
        categorySpinner = v.findViewById(R.id.item_detail_category_spiner);
        paymentSpinner = v.findViewById(R.id.item_detail_payment_spiner);


        shar = new SharedPreference();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletItem();

            }
        });

        //------spinner for category ----------
        getExpenseCategory();
        categorySpinner.setSelection(item.getExpenseId() - 65);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expenseId = expenseData.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //------spinner for currency ----------
        getinComeseCategory();
        paymentSpinner.setSelection(item.getIncomeId() - 65);
        paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incomeId = incomeData.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        name.setText(item.getName());
        price.setText(Integer.toString(item.getPrice()));
        note.setText(item.getNote());


        return v;
    }


    private void getExpenseCategory() {


        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, expenseCategoryUrl, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
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

                            categoryAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, expenseData);
                            categorySpinner.setAdapter(categoryAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            getExpenseCategory();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        getExpenseCategory();


                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getActivity().getApplicationContext()));

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

                            incomeAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, incomeData);
                            paymentSpinner.setAdapter(incomeAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            getinComeseCategory();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        getinComeseCategory();


                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getActivity()));

                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

    }

    void deletItem() {

        final JSONObject updateObject = new JSONObject();
        try {
            updateObject.put("id", item.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Initialize a new JsonObjectRequest instance
        String vv = deleteUrl + "?id=" + item.getId();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, vv, updateObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            Toast.makeText(getContext(), getString(R.string.delete), Toast.LENGTH_LONG).show();

                            if (message.equals("Delete is Success")) {
                                name.setText("");
                                note.setText("");
                                price.setText("");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            deletItem();
                            //   Toast.makeText(getContext(), "message" + e.getMessage(), Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        deletItem();
                        // Do something when error occurred
                        //  Toast.makeText(getContext(), "nnn" + error.getMessage(), Toast.LENGTH_LONG).show();


                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getActivity()));
                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

    }

    void updateItem() {
        final String nam = name.getText().toString();
        String not = note.getText().toString();
        String val = price.getText().toString();
        final int pric = Integer.parseInt(val);
        if (incomeId == -1 || expenseId == -1 | nam.equals("") | val.equals("")) {
            Toast.makeText(getActivity(), "Please complete data ", Toast.LENGTH_LONG).show();

        } else {

            final JSONObject updateObject = new JSONObject();
            try {
                updateObject.put("id", item.getId());
                updateObject.put("Name", nam);
                updateObject.put("Notes", not);
                updateObject.put("Price", pric);
                updateObject.put("IncomeCategoryId", incomeId);
                updateObject.put("OutComeCategoryId", expenseId);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Initialize a new JsonObjectRequest instance
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, itemUrl, updateObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                                String message = response.getString("RequstDetails");
                                Toast.makeText(getContext(), getString(R.string.update_done), Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                updateItem();
                                // Toast.makeText(getContext(), "message" + e.getMessage(), Toast.LENGTH_LONG).show();

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            updateItem();
                            // Do something when error occurred
                            // Toast.makeText(getContext(), "nnn" + error.getMessage(), Toast.LENGTH_LONG).show();


                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", shar.getValue(getActivity()));
                    return params;
                }
            };

            // Add JsonObjectRequest to the RequestQueue
            MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

        }


    }
}
