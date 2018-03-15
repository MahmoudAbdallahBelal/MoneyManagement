package mab.moneymanagement.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;

import mab.moneymanagement.R;
import mab.moneymanagement.view.activity.LoginActivity;


public class ForgetPasswordFragment extends Fragment {

    EditText et_Email;
    Button verifiy;
    AlertDialog.Builder builder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forget_password, container, false);
        et_Email = (EditText) v.findViewById(R.id.forget__et_emal);
        verifiy = (Button) v.findViewById(R.id.forget_password_reset);
        builder = new AlertDialog.Builder(getContext());


        verifiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();

            }
        });

        return v;
    }

    void showDialog() {
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle(getString(R.string.forget_password_reset));
//        builder.setMessage(getString(R.string.reset_new_password));
//

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View login_layout = inflater.inflate(R.layout.verify_password, null);

        EditText code= login_layout.findViewById(R.id.verify_password_code);
        EditText newPassword = login_layout.findViewById(R.id.verify_password_new_password);


        builder.setView(login_layout);

        //SET BUTTON
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //Check Validation



            }

        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.show();

    }

}
