package com.example.firstproject_eat.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.firstproject_eat.R;
import com.example.firstproject_eat.Utilities;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText emailEt, passwordEt;
    Button loginBtn, registerBtn;
    TextView credits;
    boolean isEmailValid;

    public static final String EMAIL_KEY = "email_login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);
        credits = findViewById(R.id.credits_tv);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        credits.setOnClickListener(this);

        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEmailValid = Utilities.isValidEmail(s.toString());

                if(!isEmailValid){
                    emailEt.setError("Invalid email");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableLoginIfReady();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.login_btn){
            doLogin();
        }else if(v.getId() == R.id.register_btn){
            Intent register = new Intent(this, RegisterActivity.class);
            startActivity(register);
        }else if(v.getId() == R.id.credits_tv){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(this.getResources().getString(R.string.github_link))));
        }
    }

    private void doLogin(){
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        if(!Utilities.isValidEmail(email)){
            emailEt.setError("Invalid email");
            return;
        }

        if(!Utilities.isValidPassword(password)){
            passwordEt.setError("Invalid password");
            return;
        }

        /*Intent welcome = new Intent(this, WelcomeActivity.class);
        welcome.putExtra(EMAIL_KEY, email);
        startActivity(welcome);*/
    }

    private void enableLoginIfReady() {
        loginBtn.setEnabled(isEmailValid);
    }
}
