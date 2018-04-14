package mab.moneymanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import mab.moneymanagement.view.activity.DetailItemActivity;
import mab.moneymanagement.view.activity.Main2Activity;
import mab.moneymanagement.view.adapter.MainItemAdapter;
import mab.moneymanagement.view.interfaces.InterfaceItem;
import mab.moneymanagement.view.model.Item;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;


public class MonthlyFragment extends Fragment implements InterfaceItem {


    MainItemAdapter adapter;
    ListView mList;
    ArrayList<Item> data = new ArrayList<>();
    SharedPreference shar;
    String monthlyUrl = URL.PATH + URL.CURRENT_MONTH;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_monthly, container, false);

        Main2Activity.setListner(this);

        mList = v.findViewById(R.id.monthly_fragment_list);

        shar = new SharedPreference();

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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, monthlyUrl, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            // Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            JSONArray arr = response.getJSONArray("data");


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
                                        jsonObject.getString("IncomeCategoryName"),
                                        jsonObject.getString("OutComeCategoryName"),
                                        jsonObject.getString("CreateDate")

                                );
                                data.add(category);


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

    @Override
    public void onClick(Item item) {
        data.add(item);
        adapter.notifyDataSetChanged();
        mList.setAdapter(adapter);

    }
}
