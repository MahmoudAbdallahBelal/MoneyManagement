package mab.moneymanagement.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import mab.moneymanagement.R;
import mab.moneymanagement.view.fragment.AllItemCategoryFragment;
import mab.moneymanagement.view.fragment.CalenderFragment;

public class AllItemCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_item_category);



        //TO PUT FRAGMENT ON ACTIVITY
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.all_item_activity, new AllItemCategoryFragment())
                    .commit();
        }


        Toolbar mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.all_item_category_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.all_item_category_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



}
