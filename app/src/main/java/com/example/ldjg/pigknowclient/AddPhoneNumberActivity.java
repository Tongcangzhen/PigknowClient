package com.example.ldjg.pigknowclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPhoneNumberActivity extends AppCompatActivity {
    @BindView(R.id.edittext_phonenumber)
    EditText editTextPhoneNumber;

    @BindView(R.id.edittext_verification_code)
    EditText editTextVerigicationCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_check_code)
    public void checkCode() {

    }

    @OnClick(R.id.button_add_phonenumber)
    public void addPhone() {

    }

    public void getCheckCode() {

    }

}
