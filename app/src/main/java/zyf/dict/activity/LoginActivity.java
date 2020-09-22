package mhy.dict.activity;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import mhy.dict.domain.User;
import mhy.dict.service.UserService;


public class LoginActivity extends Activity {

    private Button loginBtn;

    private Button registbutton;

    private EditText etUsername;

    private EditText etPwd;

    UserService us;

    MyApplication myApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        myApp = MyApplication.getInstance();

        us = new UserService(LoginActivity.this);

        etUsername = (EditText) findViewById(R.id.etUsername);

        etPwd = (EditText) findViewById(R.id.etPwd);

        loginBtn = (Button) findViewById(R.id.btLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().length() < 1) {
                    Toast.makeText(LoginActivity.this, "��½�˺Ų���Ϊ��!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (etPwd.getText().toString().length() < 1) {
                    Toast.makeText(LoginActivity.this, "��½���벻��Ϊ��!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                User user = us.getUserByLoginNameAndPsw(etUsername.getText().toString(), etPwd.getText().toString());

                if (user != null) {

                    myApp.setUser(user);

                    Intent intent = new Intent();

                    intent.setClass(LoginActivity.this, MainActivity.class);

                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "��½ʧ��,�����½�˺ź������Ƿ���ȷ!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        registbutton = (Button) findViewById(R.id.btRegist);
        registbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                intent.setClass(LoginActivity.this, RegisterActivity.class);

                startActivity(intent);


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
