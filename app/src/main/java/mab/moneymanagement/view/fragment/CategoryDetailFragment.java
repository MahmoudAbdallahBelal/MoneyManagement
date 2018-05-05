package mab.moneymanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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
import com.labo.kaji.fragmentanimations.CubeAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.model.User;
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

    User user;


    int allMoney = 0;
    int flag = -1;


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
        int id = getNameIcon(categoryData.getIcon());
        iconImage.setImageResource(getIcomImage(id));


        final ArrayAdapter<CharSequence> iconAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.icon, android.R.layout.simple_spinner_item);
        iconAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iconSpinner.setAdapter(iconAdapter);
        iconSpinner.setSelection(getNameIcon(categoryData.getIcon()));


        iconSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = iconSpinner.getItemAtPosition(position).toString();
                if (flag == -1) {
                    selectedCategory = categoryData.getIcon();

                }
                flag = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = categoryData.getIcon();

            }
        });

        try {
            iconImage.setImageResource(getIcomImage(getNameIcon(selectedCategory)));

        } catch (Exception e) {

        }


        getExpenseCategory();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBudget();
                int vv;
                if (categoryData.getKind().equals("expense")) {
                    String nam = name.getText().toString();
                    String valu = value.getText().toString();
                    vv = Integer.parseInt(valu);

                    if (nam.equals("") || valu.equals("")) {
                        Toast.makeText(getActivity(), getString(R.string.complete_data), Toast.LENGTH_SHORT).show();

                    } else if (nam.equals(categoryData.getName()) && vv == categoryData.getBudget() && selectedCategory == categoryData.getIcon()) {

                        Toast.makeText(getActivity(), getString(R.string.no_changes), Toast.LENGTH_SHORT).show();

                    } else {
                        updateCategoryExpense(nam, vv, selectedCategory, categoryData.getId());

                    }


                }
                if (categoryData.getKind().equals("income")) {

                    String nam = name.getText().toString();
                    String valu = value.getText().toString();
                    int vvv = Integer.parseInt(valu);

                    if (nam.equals("") || valu.equals("")) {
                        Toast.makeText(getActivity(), getString(R.string.complete_data), Toast.LENGTH_SHORT).show();

                    } else if (nam.equals(categoryData.getName()) && vvv == categoryData.getMoney() && selectedCategory == categoryData.getIcon()) {

                        Toast.makeText(getActivity(), getString(R.string.no_changes), Toast.LENGTH_SHORT).show();

                    } else {
                        updateCategoryIncome(nam, vvv, selectedCategory, categoryData.getId());

                    }

                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategory(categoryData.getId());


            }
        });

        if (categoryData.getKind().equals("income")) {
            int valu = categoryData.getMoney();
            value.setText(valu + "");

        }

        if (categoryData.getKind().equals("expense")) {
            int valu = categoryData.getBudget();
            value.setText(valu + "");

        }
        name.setText(categoryData.getName());
        iconSpinner.setSelection(0);


        return v;
    }

    private void deleteCategory(final int id) {


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

                            Toast.makeText(getContext(), getString(R.string.delete), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getActivity(), mab.moneymanagement.view.activity.Category.class);
                            startActivity(i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            deleteCategory(id);
                            // Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        //  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        deleteCategory(id);

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


    private void updateCategoryIncome(final String categoryName, final int value, final String selectedCategory, final int id) {


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
                            // Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            // Toast.makeText(getContext(), "2222222222", Toast.LENGTH_LONG).show();

                            //  Toast.makeText(getContext(), getString(R.string.update_done), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getActivity(), mab.moneymanagement.view.activity.Category.class);
                            startActivity(i);
                            getActivity().finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            updateCategoryIncome(categoryName, value, selectedCategory, id);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        updateCategoryIncome(categoryName, value, selectedCategory, id);

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

    private void updateCategoryExpense(final String categoryName, final int value, final String selectedCategory, final int id) {


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

                            // Toast.makeText(getContext(), getString(R.string.update_done), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getActivity(), mab.moneymanagement.view.activity.Category.class);
                            startActivity(i);
                            getActivity().finish();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            updateCategoryExpense(categoryName, value, selectedCategory, id);
                            //Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        updateCategoryExpense(categoryName, value, selectedCategory, id);


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

    private int getNameIcon(String name) {
        if (name.equals(getString(R.string.food))) {
            return 0;
        } else if (name.equals(getString(R.string.home))) {
            return 1;
        } else if (name.equals(getString(R.string.personal))) {
            return 2;
        } else if (name.equals(getString(R.string.salary))) {
            return 3;
        } else if (name.equals(getString(R.string.saving))) {
            return 4;
        } else if (name.equals(getString(R.string.shopping))) {
            return 5;
        } else if (name.equals(getString(R.string.child))) {
            return 6;
        } else if (name.equals(getString(R.string.car))) {
            return 7;
        } else if (name.equals(getString(R.string.kast))) {
            return 8;
        } else
            //credit
            return 9;
    }

    private int getExpenseCategory() {


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


                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObject = arr.getJSONObject(i);
                                allMoney += jsonObject.getInt("Budget");
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

        return allMoney;
    }

    void checkBudget() {
        user = shar.getUser(getContext());
        int flag = user.getBadgetValue();
        int count = 0;
        if (flag != 0) {
            int xx = getExpenseCategory();
            count = flag / 2;

            if (xx > count) {
                Toast.makeText(getContext(), getString(R.string.category_budget) + user.getBadgetValue(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.DOWN, enter, 600);
    }

}
