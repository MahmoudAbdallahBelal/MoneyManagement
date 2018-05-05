package mab.moneymanagement.view.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

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

    ProgressDialog progressDialog;

    MDToast mdToast;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forget_password, container, false);
        et_Email = v.findViewById(R.id.forget__et_emal);
        verifiy = v.findViewById(R.id.forget_password_reset);
        builder = new AlertDialog.Builder(getContext());

        progressDialog  = new ProgressDialog(getActivity());


        verifiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    verfiyEmail();

                } catch (Exception e) {

                }

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
        builder.setPositiveButton(getString(R.string.done), new DialogInterface.OnClickListener() {
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
                                        if(message.equals("Password is Reset Success")) {
                                            mdToast = MDToast.makeText(getActivity(),message.toString() , 8,MDToast.TYPE_SUCCESS );
                                            mdToast.show();

                                            getActivity().finish();
                                        }

                                        else {

                                            mdToast = MDToast.makeText(getActivity(),message.toString() , 8,MDToast.TYPE_ERROR );
                                            mdToast.show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                        //Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

                                        mdToast = MDToast.makeText(getActivity(),e.toString() , 8,MDToast.TYPE_ERROR );
                                        mdToast.show();
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
            Toast.makeText(getActivity(), getString(R.string.complete_data), Toast.LENGTH_SHORT).show();
        } else {


            progressDialog.setMessage(getString(R.string.wait));
            progressDialog.show();
            // Initialize a new JsonObjectRequest instance
            //   RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String ff = ForgetUrl + "?Email=" + email;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ff, (String) null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                progressDialog.dismiss();
                                //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                                String message = response.getString("RequstDetails");
                                if (message.equals("Email is Send Please Check Email")) {
                                    showDialog();
                                } else if (message.equals("Failed to Send Email")) {

                                    mdToast = MDToast.makeText(getActivity(),getString(R.string.fail_to_send) , 8,MDToast.TYPE_ERROR );
                                    mdToast.show();
                                }
                                else if (message.equals("Email Not Found In System Please Get Register")) {

                                    mdToast = MDToast.makeText(getActivity(),getString(R.string.email_not_found) , 8,MDToast.TYPE_ERROR );
                                    mdToast.show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                verfiyEmail();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            verfiyEmail();
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
            //requestQueue.add(jsonObjectRequest);
            MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

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
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.DOWN, enter, 700);
    }

}

