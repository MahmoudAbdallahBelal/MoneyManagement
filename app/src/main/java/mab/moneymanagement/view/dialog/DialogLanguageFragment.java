package mab.moneymanagement.view.dialog;

import android.app.DialogFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


import mab.moneymanagement.R;

/**
 * Created by Gihan on 3/2/2018.
 */

public class DialogLanguageFragment extends DialogFragment {

    static DialogLanguageFragment newInstance() {
        return new DialogLanguageFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.language, container, false);

        RadioButton arabic=(RadioButton)v.findViewById(R.id.select_language_arabic);

        RadioButton english=(RadioButton)v.findViewById(R.id.select_language_english);

        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }


}
