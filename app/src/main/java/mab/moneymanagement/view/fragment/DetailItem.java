package mab.moneymanagement.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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
import com.labo.kaji.fragmentanimations.CubeAnimation;

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


    public static int incomeId = -1;
    public static int expenseId = -1;
    int cateogryIncomId = -1;
    int cateogryExpenseId = -1;
    public static int x = 0;
    public static int x1 = 0;

    Item item;

    @Override
    public void onStart() {
        super.onStart();
        getinComeseCategory();
        getExpenseCategory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_item, container, false);

        item = (Item) getActivity().getIntent().getSerializableExtra("item");
        shar = new SharedPreference();


        name = v.findViewById(R.id.item_detail_et_item_name);
        price = v.findViewById(R.id.item_detail_price);
        note = v.findViewById(R.id.item_detail_et_note);
        update = v.findViewById(R.id.item_detail_update);
        delete = v.findViewById(R.id.item_detail_delete);
        categorySpinner = v.findViewById(R.id.item_detail_category_spiner);
        paymentSpinner = v.findViewById(R.id.item_detail_payment_spiner);


        //   ------spinner for expense ----------
        getExpenseCategory();
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cateogryExpenseId = expenseData.get(position).getId();
                // Toast.makeText(getActivity(),"55555",Toast.LENGTH_SHORT).show();
                x += 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//

        //------spinner for income ----------

        paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cateogryIncomId = incomeData.get(position).getId();
                x1 += 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        name.setText(item.getName());
        price.setText(Integer.toString(item.getPrice()));
        note.setText(item.getNote());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || price.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.complete_data), Toast.LENGTH_SHORT).show();

                } else {
                    deletItem();
                }


            }
        });

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
                                if (item.getExpenseId() == jsonObject.getInt("Id")) {
                                    expenseId = i;
                                }

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
                            categorySpinner.setAdapter(categoryAdapter);
                            categorySpinner.setSelection(expenseId);


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
                                if (item.getIncomeId() == jsonObject.getInt("Id")) {
                                    incomeId = i;
                                }
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
                            paymentSpinner.setSelection(incomeId);


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
        if (nam.equals("") | val.equals("")) {
            Toast.makeText(getActivity(), getString(R.string.complete_data), Toast.LENGTH_LONG).show();

        } else if (name.getText().toString().equals("") || price.getText().toString().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.complete_data), Toast.LENGTH_SHORT).show();

        } else {

            final JSONObject updateObject = new JSONObject();
            try {
                updateObject.put("id", item.getId());
                updateObject.put("Name", nam);
                updateObject.put("Notes", not);
                updateObject.put("Price", pric);
                updateObject.put("IncomeCategoryId", cateogryIncomId);
                updateObject.put("OutComeCategoryId", cateogryExpenseId);

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
                                if (message.equals("Save Changes is Success")) {
                                    Toast.makeText(getContext(), getString(R.string.update_done), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), getString(R.string.error_happen), Toast.LENGTH_LONG).show();

                                }
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

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.DOWN, enter, 700);
    }
}
