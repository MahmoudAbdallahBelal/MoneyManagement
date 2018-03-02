package mab.moneymanagement.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import mab.moneymanagement.R;
import mab.moneymanagement.view.activity.CompleteLoginActivity;
import mab.moneymanagement.view.activity.Main2Activity;


public class LoginFragment extends Fragment {

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail=(EditText)v.findViewById(R.id.login_et_email);
        etPassword=(EditText)v.findViewById(R.id.login_et_password);
        btnLogin=(Button)v.findViewById(R.id.login_btn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent completeIntent=new Intent(getActivity(), CompleteLoginActivity.class);
                startActivity(completeIntent);

            }
        });




        return v;
    }


}
