package mab.moneymanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
import mab.moneymanagement.view.activity.DetailItemActivity;
import mab.moneymanagement.view.adapter.MainItemAdapter;
import mab.moneymanagement.view.model.Item;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class AllItemChooseDateFragment extends Fragment {


    MainItemAdapter adapter;
    ListView mList;
    ArrayList<Item> data = new ArrayList<>();

    String dailyUrl = URL.PATH + URL.DAILY_URL;
    SharedPreference shar;
    int day;
    int month;
    int year;
    TextView tvMessage;

    @Override
    public void onStart() {
        super.onStart();

        getAllItem();
    }

    @Override
    public void onPause() {
        super.onPause();
        getAllItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_item_choose_date, container, false);

        //--get category data from intent to put in menu -------

        day = getActivity().getIntent().getIntExtra("day", -1);
        month = getActivity().getIntent().getIntExtra("month", -1);
        year = getActivity().getIntent().getIntExtra("year", -1);

        shar = new SharedPreference();


        tvMessage = v.findViewById(R.id.all_item_choose_date_tv_message);
        mList = v.findViewById(R.id.all_item_calender_list);
        getAllItem();


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

    private void getAllItem() {


        // Initialize a new JsonObjectRequest instance
        String vv = dailyUrl + "?Month=" + month + "&Year=" + year + "&Day=" + day;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, vv, (String) null,
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
                            try {
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
                            } catch (Exception e) {

                            }


                            adapter = new MainItemAdapter(getContext(), data);
                            mList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();


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
            return getContext().getString(R.string.food);
        } else if (name.equals("Home")) {
            return getContext().getString(R.string.home);
        } else if (name.equals("Personal")) {
            return getContext().getString(R.string.personal);
        } else if (name.equals("Salary")) {
            return getContext().getString(R.string.salary);
        } else if (name.equals("Saving")) {
            return getContext().getString(R.string.saving);
        } else if (name.equals("Shopping")) {
            return getContext().getString(R.string.shopping);
        } else if (name.equals("Child")) {
            return getContext().getString(R.string.child);
        } else if (name.equals("Car")) {
            return getContext().getString(R.string.car);
        } else if (name.equals("Kast")) {
            return getContext().getString(R.string.kast);
        } else if (name.equals("Credit")) {
            return getContext().getString(R.string.credit);
        } else
            return name;


    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.DOWN, enter, 700);
    }


}
