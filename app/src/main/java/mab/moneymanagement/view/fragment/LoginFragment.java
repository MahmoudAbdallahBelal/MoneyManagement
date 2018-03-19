package mab.moneymanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.activity.ForgetPasswordActivity;
import mab.moneymanagement.view.activity.RegestrationActivity;
import mab.moneymanagement.view.activity.Main2Activity;
import mab.moneymanagement.view.model.User;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;


public class LoginFragment extends Fragment {

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    TextView tvRegester;
    TextView restePassword;
    String email;
    String password;
    SharedPreference cc = new SharedPreference();
    URL url = new URL();

    String login_url = URL.PATH + URL.LOGIN;

    MaterialDialog.Builder loginDaolog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = v.findViewById(R.id.login_et_email);
        etPassword = v.findViewById(R.id.login_et_password);
        btnLogin = v.findViewById(R.id.login_btn);
        tvRegester = v.findViewById(R.id.login_tv_crate_account);
        restePassword = v.findViewById(R.id.login_tv_forget_password);


        loginDaolog = new MaterialDialog.Builder(getContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

        tvRegester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent completeIntent = new Intent(getActivity(), RegestrationActivity.class);
                startActivity(completeIntent);

            }
        });

        restePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent completeIntent = new Intent(getActivity(), ForgetPasswordActivity.class);
                startActivity(completeIntent);

            }
        });

        return v;
    }

    private void login() {
        loginDaolog.title(getString(R.string.login_toolbar)).content("please wait until login ....").show();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        //-----check validate -------------
        if (email.equals("") || password.equals("")) {
            Toast.makeText(getContext(), "Complete data ", Toast.LENGTH_LONG).show();
            loginDaolog.build().dismiss();


        } else {

            final JSONObject loginObject = new JSONObject();
            try {
                loginObject.put("Email", email);
                loginObject.put("Password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Initialize a new RequestQueue instance
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            // Initialize a new JsonObjectRequest instance
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, login_url, loginObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                                String message = response.getString("RequstDetails");
                                String accessTocken = response.getString("access_token");
                                String authorization = response.getString("token_type");


                                if (message.equals("loginSuccess")) {
                                    loginDaolog.build().dismiss();
                                    Toast.makeText(getContext(), "Sucess login ", Toast.LENGTH_LONG).show();

                                    //save id  in shared prefrence

                                    SharedPreference shar = new SharedPreference();
                                    shar.save(getActivity(), accessTocken, authorization);

                                    Intent mainIntent = new Intent(getActivity(), Main2Activity.class);
                                    startActivity(mainIntent);
                                    getActivity().finish();


                                } else {
                                    loginDaolog.build().dismiss();
                                    Toast.makeText(getContext(), "please check email correctly  ", Toast.LENGTH_LONG).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                loginDaolog.build().dismiss();

                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG);

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Do something when error occurred

                        }
                    });

            // Add JsonObjectRequest to the RequestQueue
            MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

        }


    }


}
