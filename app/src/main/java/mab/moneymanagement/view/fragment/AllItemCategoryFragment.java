package mab.moneymanagement.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.util.ArrayList;

import mab.moneymanagement.R;
import mab.moneymanagement.view.activity.CategoryDetailActivity;
import mab.moneymanagement.view.activity.DetailItemActivity;
import mab.moneymanagement.view.adapter.MainItemAdapter;
import mab.moneymanagement.view.model.Category;
import mab.moneymanagement.view.model.Item;

public class AllItemCategoryFragment extends Fragment {


    MainItemAdapter adapter;
    ListView mList;
    ArrayList<Item> data = new ArrayList<>();
    Category categoryDatd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_item_category, container, false);

        //--get category data from intent to put in menu -------
         categoryDatd= (Category) getActivity().getIntent().getSerializableExtra("categoryData");




        mList = (ListView) v.findViewById(R.id.all_item_category_list);

        data.add(new Item("Rice", 12.5, "Price is cheap", "Food", "cash", "12/2/2015"));
        data.add(new Item("Rice", 12.5, "Price is cheap", "Food", "cash", "12/2/2015"));
        data.add(new Item("Rice", 12.5, "Price is cheap", "Food", "cash", "12/2/2015"));
        data.add(new Item("Rice", 12.5, "Price is cheap", "Food", "cash", "12/2/2015"));
        data.add(new Item("Rice", 12.5, "Price is cheap", "Food", "cash", "12/2/2015"));

        adapter = new MainItemAdapter(getContext(), data);
        mList.setAdapter(adapter);


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
            categiryIntent.putExtra("category",categoryDatd);

            startActivity(categiryIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
