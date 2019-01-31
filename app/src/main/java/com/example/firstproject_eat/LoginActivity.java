package com.example.firstproject_eat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText emailEt, passwordEt;
    Button loginBtn, registerBtn;

    public static final String EMAIL_KEY = "email_login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.login_btn){
            doLogin();
        }else if(v.getId() == R.id.register_btn){
            Intent register = new Intent(this, RegisterActivity.class);
            startActivity(register);
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
}
