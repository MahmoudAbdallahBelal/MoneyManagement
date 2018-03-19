package mab.moneymanagement.view.fragment;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;


public class ForgetPasswordFragment extends Fragment {

    EditText et_Email;
    Button verifiy;
    AlertDialog.Builder builder;
    String ForgetUrl = URL.PATH + URL.FORGET_PASSWORD;
    String email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forget_password, container, false);
        et_Email = v.findViewById(R.id.forget__et_emal);
        verifiy = v.findViewById(R.id.forget_password_reset);
        builder = new AlertDialog.Builder(getContext());


        verifiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verfiyEmail();

            }
        });

        return v;
    }

    void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View login_layout = inflater.inflate(R.layout.verify_password, null);

        final EditText et_code = login_layout.findViewById(R.id.verify_password_code);
        final EditText et_newPassword = login_layout.findViewById(R.id.verify_password_new_password);


        builder.setView(login_layout);

        //SET BUTTON
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //Check Validation

                //---------------------------------
                String code = et_code.getText().toString();
                String newPassword = et_newPassword.getText().toString();


                if (code.equals("") || newPassword.equals("")) {

                } else {

                    int cc = Integer.parseInt(code);

                    final JSONObject regsterObject = new JSONObject();
                    try {
                        regsterObject.put("Email", email);
                        regsterObject.put("Password", newPassword);
                        regsterObject.put("Code", cc);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Initialize a new JsonObjectRequest instance
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ForgetUrl, regsterObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {

                                        //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                                        String message = response.getString("RequstDetails");


                                        Toast.makeText(getContext(), message.toString(), Toast.LENGTH_LONG).show();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                                    }


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Do something when error occurred
                                    verfiyEmail();

                                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

                                }
                            });

                    // Add JsonObjectRequest to the RequestQueue
                    MysingleTon.getInstance(getActivity().getApplicationContext()).addToRequestqueue(jsonObjectRequest);

                }
                //----

                //---------------------------------

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

    void verfiyEmail() {
        email = et_Email.getText().toString();
        if (email.equals("")) {
        } else {


//
//            final JSONObject regsterObject = new JSONObject();
//            try {
//                regsterObject.put("Email", email);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            // Initialize a new JsonObjectRequest instance
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ForgetUrl, (String) null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                                String message = response.getString("RequstDetails");
                                if (message.equals("Email is Send Please Check Email")) {
                                    showDialog();
                                }


                                Toast.makeText(getContext(), message.toString(), Toast.LENGTH_LONG).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Do something when error occurred
                            verfiyEmail();

                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("Email", email);
                    return param;
                }
            };

            // Add JsonObjectRequest to the RequestQueue
            MysingleTon.getInstance(getActivity().getApplicationContext()).addToRequestqueue(jsonObjectRequest);

        }
        //----
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().finish();

    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();

    }
}
