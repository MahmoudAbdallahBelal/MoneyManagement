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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;


public class CategoryDetailFragment extends Fragment {
    EditText name;
    EditText value;
    ImageView iconImage;
    Button delete, update;

    String selectedCategory;

    String incomeCategoryUrl = URL.PATH + URL.CATEGORY_INCOME;
    String expenseCategoryUrl = URL.PATH + URL.CATEGORY_EXPENSE;
    String deleteCategoryUrl = URL.PATH + URL.DELETE_CATEGORY;
    Spinner iconSpinner;

    Category categoryData;
    SharedPreference shar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //--get category data from intent to put in view -------
        categoryData = (Category) getActivity().getIntent().getSerializableExtra("category");
        shar = new SharedPreference();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category_detail, container, false);
        name = v.findViewById(R.id.category_detail_name);
        value = v.findViewById(R.id.category_detail_value);
        iconImage = v.findViewById(R.id.category_detail_icon);
        update = v.findViewById(R.id.category_detail_update_btn);
        delete = v.findViewById(R.id.category_detail_delete_btn);

        //------spinner for icon ----------
        iconSpinner = v.findViewById(R.id.category_detail_spinner_icon);
        iconSpinner.setSelection(getNameIcon(categoryData.getIcon()));

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


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (categoryData.getKind().equals("expense")) {
                    String nam = name.getText().toString();
                    String valu = value.getText().toString();
                    int vv = Integer.parseInt(valu);

                    if (nam.equals("") || valu.equals("")) {

                    } else {
                        updateCategoryExpense(nam, vv, selectedCategory, categoryData.getId());

                    }


                }
                if (categoryData.getKind().equals("income")) {
                    String nam = name.getText().toString();
                    String valu = value.getText().toString();
                    int vv = Integer.parseInt(valu);

                    if (nam.equals("") || valu.equals("")) {

                    } else {
                        updateCategoryIncome(nam, vv, selectedCategory, categoryData.getId());

                    }

                } else {
                    Toast.makeText(getActivity(), "sorry error happen", Toast.LENGTH_LONG).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategory(categoryData.getId());

            }
        });


        name.setText(categoryData.getName());
        int valu = categoryData.getBudget();
        value.setText(valu + "");
        iconSpinner.setSelection(0);


        return v;
    }

    private void deleteCategory(int id) {


        final JSONObject regsterObject = new JSONObject();
        try {


            regsterObject.put("Id", id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String ll = deleteCategoryUrl + "?id=" + id;
        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ll, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");

                            name.setText("");
                            value.setText("");

                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

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
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

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

    private void updateCategoryIncome(String categoryName, int value, String selectedCategory, int id) {


        final JSONObject regsterObject = new JSONObject();
        try {


            regsterObject.put("Name", categoryName);
            regsterObject.put("Icon", selectedCategory);
            regsterObject.put("Price", value);
            regsterObject.put("Id", id);


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

                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

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
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

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

    private void updateCategoryExpense(String categoryName, int value, String selectedCategory, int id) {


        final JSONObject regsterObject = new JSONObject();
        try {


            regsterObject.put("Name", categoryName);
            regsterObject.put("Icon", selectedCategory);
            regsterObject.put("Price", value);
            regsterObject.put("Id", id);


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

                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

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
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

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

    private int getNameIcon(String name) {
        if (name.equals("Food")) {
            return 0;
        } else if (name.equals("Home")) {
            return 1;


        } else if (name.equals("Personal")) {
            return 2;

        } else if (name.equals("Salary")) {
            return 3;

        } else if (name.equals("Saving")) {
            return 4;

        } else if (name.equals("Shopping")) {
            return 5;

        } else if (name.equals("Child")) {
            return 6;

        } else if (name.equals("Car")) {
            return 7;

        } else if (name.equals("Kast")) {

            return 8;

        } else
            return 9;


    }

}
