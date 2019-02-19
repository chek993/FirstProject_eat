package com.example.firstproject_eat.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.firstproject_eat.R;
import com.example.firstproject_eat.Utilities;
import com.example.firstproject_eat.datamodels.User;
import com.example.firstproject_eat.services.RestController;
import com.example.firstproject_eat.ui.SharedPreferencesUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener{

    EditText insertUsernameEt, insertEmailEt, insertPasswordEt, insertPhoneEt;
    Button registerBtn;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private boolean isUsernameValid, isEmailValid, isPasswordValid, isPhoneValid;

    RestController restController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        insertUsernameEt = findViewById(R.id.insert_username_et);
        insertEmailEt = findViewById(R.id.insert_email_et);
        insertPasswordEt = findViewById(R.id.insert_password_et);
        insertPhoneEt = findViewById(R.id.telephone_number_et);
        registerBtn = findViewById(R.id.register_btn);

        restController =  new RestController(this);
        registerBtn.setOnClickListener(this);

        insertUsernameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isUsernameValid = Utilities.isValidUsername(s.toString());

                if(!isUsernameValid){
                    insertUsernameEt.setError("Invalid username");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableRegisterIfReady();
            }
        });

        insertEmailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEmailValid = Utilities.isValidEmail(s.toString());

                if(!isEmailValid){
                    insertEmailEt.setError("Invalid email");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableRegisterIfReady();
            }
        });

        insertPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPasswordValid = Utilities.isValidPassword(s.toString());

                if(!isPasswordValid){
                    insertPasswordEt.setError("Invalid password");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableRegisterIfReady();
            }
        });

        insertPhoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPhoneValid = Utilities.isValidPhone(s.toString());

                if(!isPhoneValid){
                    insertPhoneEt.setError("Invalid telephone number");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableRegisterIfReady();
            }
        });
    }

    @Override
    public void onClick(View v) {
        doRegister();
    }

    private void doRegister(){

        Map<String, String> params = new HashMap<>();
        params.put("username", insertUsernameEt.getText().toString());
        params.put("email", insertEmailEt.getText().toString());
        params.put("password", insertPasswordEt.getText().toString());
        //params.put("telephone", insertPhoneEt.getText().toString());

        restController.postRequest(User.REGISTER_ENDPOINT, params,this,this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.getMessage());
        Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG, response);
        try {
            JSONObject responseJson = new JSONObject(response);
            String accessToken = responseJson.getString("jwt");
            SharedPreferencesUtilities.putValue(this, User.ACCESS_TOKEN_KEY, accessToken);

            User user = new User(responseJson.getJSONObject("user"), accessToken);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void enableRegisterIfReady() {
        registerBtn.setEnabled(isUsernameValid && isEmailValid && isPasswordValid && isPhoneValid);
    }
}