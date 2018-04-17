package mab.moneymanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import mab.moneymanagement.view.activity.CategoryDetailActivity;
import mab.moneymanagement.view.activity.DetailItemActivity;
import mab.moneymanagement.view.adapter.MainItemAdapter;
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.model.Item;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class AllItemCategoryFragment extends Fragment {


    MainItemAdapter adapter;
    ListView mList;
    ArrayList<Item> data = new ArrayList<>();
    Category categoryDatd;
    int postionCategory;
    TextView tvMessage;

    String itemUrl = URL.PATH + URL.ITEM_CATEGORY;

    SharedPreference shar;

    @Override
    public void onStart() {
        super.onStart();

        try {
            getAllItem();

        } catch (Exception e) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            getAllItem();

        } catch (Exception e) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_item_category, container, false);

        //--get category data from intent to put in menu -------
        categoryDatd = (Category) getActivity().getIntent().getSerializableExtra("categoryData");
        postionCategory = getActivity().getIntent().getIntExtra("id", 0);

        shar = new SharedPreference();

        tvMessage = v.findViewById(R.id.all_item_cateogry_tv_message);

        mList = v.findViewById(R.id.all_item_category_list);
        try {
            getAllItem();

        } catch (Exception e) {

        }


        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = data.get(position);
                Intent detailIntent = new Intent(getActivity(), DetailItemActivity.class);
                detailIntent.putExtra("item", item);
                startActivity(detailIntent);

            }
        });


        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.category, menu);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_category) {
            Intent categiryIntent = new Intent(getActivity(), CategoryDetailActivity.class);
            categiryIntent.putExtra("category", categoryDatd);


            startActivity(categiryIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAllItem() {


        // Initialize a new JsonObjectRequest instance
        String vvv = itemUrl + "?id=" + categoryDatd.getId();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, vvv, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            //  Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            JSONArray arr = response.getJSONArray("data");

                            if (arr.length() == 0) {
                                tvMessage.setVisibility(View.VISIBLE);
                                tvMessage.setText(getString(R.string.no_item_added));
                            }

                            data.clear();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObject = arr.getJSONObject(i);
                                Item category = new Item(
                                        jsonObject.getInt("Id"),
                                        jsonObject.getString("Name"),
                                        jsonObject.getString("Notes"),
                                        jsonObject.getInt("Price"),
                                        jsonObject.getInt("IncomeCategoryId"),
                                        jsonObject.getInt("OutComeCategoryId"),
                                        changeName(jsonObject.getString("IncomeCategoryName")),
                                        changeName(jsonObject.getString("OutComeCategoryName")),
                                        jsonObject.getString("CreateDate")

                                );
                                data.add(category);


                            }

                            adapter = new MainItemAdapter(getContext(), data);
                            mList.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();

                            getAllItem();
                            // Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        getAllItem();


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
