package mab.moneymanagement.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import mab.moneymanagement.R;
import mab.moneymanagement.view.fragment.AllItemCategoryFragment;
import mab.moneymanagement.view.fragment.AllItemChooseDateFragment;

public class AllItemChooseDate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_item_choose_date);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.all_item_calender_activity, new AllItemChooseDateFragment())
                    .commit();
        }

        Toolbar mToolbar = findViewById(R.id.all_item_calender_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.all_item_inday));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
}
