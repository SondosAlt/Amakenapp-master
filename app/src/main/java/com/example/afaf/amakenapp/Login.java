package com.example.afaf.amakenapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener{
    /*
    * =====param send in php file
    * 'userEmail','password'
    * */
    private EditText editEmail, editPassword;

    private Button login_SignIn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_SignIn = (Button) findViewById(R.id.login_SingIn);
        editEmail = (EditText) findViewById(R.id.login_Email);
        editPassword = (EditText) findViewById(R.id.login_Password);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        login_SignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == login_SignIn) {
            singIn();
            finish();
        }

    }

    public void singIn(){
        final String userEmail = editEmail.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();
        progressDialog.show();

        StringRequest send = new  StringRequest(Request.Method.POST,
                                                Constants.URL_LOGIN,
                                                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        /*
                       SharedPrefManager.getInstance(getApplicationContext())
                               .userLogin(
                                       obj.getInt("id"),
                                       obj.getString("user_email"),
                                       obj.getString("user_password"),
                                       obj.getString("user_name"),
                                       obj.getString("user_gender"),
                                       obj.getInt("country_id"),
                                       obj.getString("country_name"),
                                       obj.getInt("city_id"),
                                       obj.getString("city_name"),
                                       obj.getInt("profile_pic_id"),
                                       obj.getString("profile_pic_url")


                                );
                                **/
                        startActivity(new Intent(getApplicationContext(), SignUpBusiness.class));
                          finish();
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                Toast.makeText(
                        getApplicationContext(),
                        error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userEmail", userEmail);
                params.put("password",password);
                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(send);

    }
}



