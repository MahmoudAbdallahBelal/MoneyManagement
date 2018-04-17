package mab.moneymanagement.view.dialog;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.activity.Main2Activity;
import mab.moneymanagement.view.adapter.CustomSpinnerAdapter;
import mab.moneymanagement.view.interfaces.InterfaceItem;
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.model.Item;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

/**
 * Created by Gihan on 3/1/2018.
 */

public class DialogAddItemFragment extends DialogFragment {


    public static InterfaceItem interfaceItem;

    public static DialogAddItemFragment getDio(InterfaceItem interfaceIt) {
        interfaceItem = interfaceIt;
        return new DialogAddItemFragment();

    }

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

    CustomSpinnerAdapter categoryAdapter;
    CustomSpinnerAdapter incomeAdapter;

    int incomeId = -1;
    int expenseId = -1;

    Item item;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View edit_layout = inflater.inflate(R.layout.add_item, container, false);


        itemName = edit_layout.findViewById(R.id.add_item_et_item_name);
        itemPrice = edit_layout.findViewById(R.id.add_item_price);
        itemNote = edit_layout.findViewById(R.id.add_item_et_note);
        Button ok = edit_layout.findViewById(R.id.add_item_ok);
        Button cancel = edit_layout.findViewById(R.id.add_item_cancel);

        expenseSpinner = edit_layout.findViewById(R.id.add_item_category_spiner);
        paymentSpinner = edit_layout.findViewById(R.id.add_item_payment_spiner);


        shar = new SharedPreference();


        //------spinner for category ----------
        getExpenseCategory();
        getinComeseCategory();
        expenseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expenseId = expenseData.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incomeId = incomeData.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addItem();
                    interfaceItem.onClick(item);
                    //  Toast.makeText(getActivity(), "done", Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    // Toast.makeText(getActivity(), "Sorry erro rhappen try again", Toast.LENGTH_LONG).show();

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getString(R.string.budget_btn_cancel), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return edit_layout;
    }


    void addItem() {
        String name = itemName.getText().toString();
        String note = itemNote.getText().toString();
        String val = itemPrice.getText().toString();
        int price = Integer.parseInt(val);

        item = new Item(0, name, note, price, incomeId, expenseId, "", "", "");


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

                                dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                addItem();

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            addItem();

                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", shar.getValue(getActivity()));
                    return params;
                }
            };

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
                                        changeName(jsonObject.getString("Name")),
                                        jsonObject.getString("Icon"),
                                        jsonObject.getInt("Money"),
                                        jsonObject.getInt("Budget"),
                                        jsonObject.getString("CreateDate"),
                                        "expense"

                                );

                                expenseData.add(category);
                            }
                            categoryAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, expenseData);
                            expenseSpinner.setAdapter(categoryAdapter);

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
                                        changeName(jsonObject.getString("Name")),
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

    public String changeName(String name) {
        if (name.equals("Food")) {
            return getString(R.string.food);
        } else if (name.equals("Home")) {
            return getString(R.string.home);
        } else if (name.equals("Personal")) {
            return getString(R.string.personal);
        } else if (name.equals("Salary")) {
            return getString(R.string.salary);
        } else if (name.equals("Saving")) {
            return getString(R.string.saving);
        } else if (name.equals("Shopping")) {
            return getString(R.string.shopping);
        } else if (name.equals("Child")) {
            return getString(R.string.child);
        } else if (name.equals("Car")) {
            return getString(R.string.car);
        } else if (name.equals("Kast")) {
            return getString(R.string.kast);
        } else if (name.equals("Credit")) {
            return getString(R.string.credit);
        } else
            return name;


    }

}
