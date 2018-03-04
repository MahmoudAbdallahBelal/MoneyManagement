package mab.moneymanagement.view.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import mab.moneymanagement.R;
import mab.moneymanagement.view.activity.Main2Activity;

/**
 * Created by Gihan on 3/1/2018.
 */

public class DialogAddItemFragment extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View edit_layout = inflater.inflate(R.layout.add_item, container, false);


//        getDialog().setTitle(R.string.add_item_title);
//
//        //                dialogFragment.getActivity().setTitle(getString());
////                dialogFragment.getActivity().setTitleColor(Color.WHITE);

        EditText itemName = edit_layout.findViewById(R.id.add_item_et_item_name);
        EditText itemPrice = edit_layout.findViewById(R.id.add_item_price);
        EditText itemNote = edit_layout.findViewById(R.id.add_item_et_note);
        Button ok = edit_layout.findViewById(R.id.add_item_ok);
        Button cancel = edit_layout.findViewById(R.id.add_item_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "add item done", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        //------spinner for category ----------
        Spinner categorySpinner = edit_layout.findViewById(R.id.add_item_category_spiner);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.category,
                android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //------spinner for currency ----------
        Spinner paymentSpinner = edit_layout.findViewById(R.id.add_item_payment_spiner);
        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.payment,
                android.R.layout.simple_spinner_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter);

        return edit_layout;
    }

}
