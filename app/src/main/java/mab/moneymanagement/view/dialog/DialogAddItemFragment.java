package mab.moneymanagement.view.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import mab.moneymanagement.view.adapter.CustomSpinnerAdapter;
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

/**
 * Created by Gihan on 3/1/2018.
 */

public class DialogAddItemFragment extends DialogFragment {

    EditText itemName;
    EditText itemPrice;
    EditText itemNote;


    String incomeCategoryUrl = URL.PATH + URL.CATEGORY_INCOME;
    String expenseCategoryUrl = URL.PATH + URL.CATEGORY_EXPENSE;
    String itemUrl = URL.PATH + URL.ITEM;


    ArrayList<Category> expenseData = new ArrayList<>();
    ArrayList<Category> incomeData = new ArrayList<>();
    SharedPreference shar;

    Spinner expenseSpinner;
    Spinner paymentSpinner;

    CustomSpinnerAdapter adapter;


    int incomeId = -1;
    int expenseId = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View edit_layout = inflater.inflate(R.layout.add_item, container, false);


        itemName = edit_layout.findViewById(R.id.add_item_et_item_name);
        itemPrice = edit_layout.findViewById(R.id.add_item_price);
        itemNote = edit_layout.findViewById(R.id.add_item_et_note);
        Button ok = edit_layout.findViewById(R.id.add_item_ok);
        Button cancel = edit_layout.findViewById(R.id.add_item_cancel);

        shar = new SharedPreference();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        //------spinner for category ----------
        getExpenseCategory();
        expenseSpinner = edit_layout.findViewById(R.id.add_item_category_spiner);
        CustomSpinnerAdapter ada = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, expenseData);
        expenseSpinner.setAdapter(ada);

        expenseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), expenseData.get(position).getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //------spinner for payment ----------
        getinComeseCategory();
        paymentSpinner = edit_layout.findViewById(R.id.add_item_payment_spiner);
        adapter = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, incomeData);
        paymentSpinner.setAdapter(adapter);
        paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), expenseData.get(position).getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return edit_layout;
    }


    void addItem() {
        String name = itemName.getText().toString();
        String note = itemNote.getText().toString();
        String val = itemPrice.getText().toString();
        int price = Integer.parseInt(val);
        if (incomeId == -1 || expenseId == -1 | name.equals("") | val.equals("")) {
            Toast.makeText(getActivity(), "Please complete data ", Toast.LENGTH_LONG).show();

        } else {


            final JSONObject updateObject = new JSONObject();
            try {
                updateObject.put("id", 0);
                updateObject.put("Name", name);
                updateObject.put("Notes", note);
                updateObject.put("Price", price);
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
                                Toast.makeText(getContext(), "message" + message, Toast.LENGTH_LONG).show();

                                dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "message" + e.getMessage(), Toast.LENGTH_LONG).show();

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Do something when error occurred
                            Toast.makeText(getContext(), "nnn" + error.getMessage(), Toast.LENGTH_LONG).show();


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
                        //  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();


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


                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
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

}
