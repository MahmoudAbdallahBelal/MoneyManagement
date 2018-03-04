package mab.moneymanagement.view.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mab.moneymanagement.R;

/**
 * Created by Gihan on 3/4/2018.
 */

public class DialogBudgetFragment extends DialogFragment {





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.budget, container, false);


        Button ok=v.findViewById(R.id.budget_btn_ok);
        Button cancel=v.findViewById(R.id.budget_btn_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return v;
    }

}
