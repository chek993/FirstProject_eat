package com.example.firstproject_eat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText insertEmailEt, insertPasswordEt, insertPhoneEt;
    Button registerBtn;

    private boolean isEmailValid, isPasswordValid, isPhoneValid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        insertEmailEt = findViewById(R.id.insert_email_et);
        insertPasswordEt = findViewById(R.id.insert_password_et);
        insertPhoneEt = findViewById(R.id.telephone_number_et);
        registerBtn = findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(this);

        insertEmailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEmailValid = Utilities.isValidEmail(s.toString());
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
        String emailRegister = insertEmailEt.getText().toString();

        /*Intent login = new Intent(this, MainActivity.class);
        login.putExtra(MainActivity.EMAIL_KEY, emailRegister);
        startActivity(login);*/
    }


    private void enableRegisterIfReady() {
        registerBtn.setEnabled(isEmailValid && isPasswordValid && isPhoneValid);
    }
}