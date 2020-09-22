package mhy.dict.activity;

import java.util.List;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mhy.dict.domain.User;
import mhy.dict.service.UserService;

public class RegisterActivity extends Activity {


    private Button registbutton;

    private EditText loginname;

    private EditText loginpsw;

    private EditText tel;

    private EditText email;

    UserService us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);

        us = new UserService(RegisterActivity.this);

        loginname = (EditText) findViewById(R.id.loginname);

        loginpsw = (EditText) findViewById(R.id.loginpsw);

        tel = (EditText) findViewById(R.id.tel);

        email = (EditText) findViewById(R.id.email);

        registbutton = (Button) findViewById(R.id.register_btn);
        registbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginname.getText().toString().length() < 1) {
                    Toast.makeText(RegisterActivity.this, "��½�˺Ų���Ϊ��!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (loginpsw.getText().toString().length() < 1) {
                    Toast.makeText(RegisterActivity.this, "��½���벻��Ϊ��!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if (tel.getText().toString().length() < 1) {
                    Toast.makeText(RegisterActivity.this, "�绰����Ϊ��!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if (email.getText().toString().length() < 1) {
                    Toast.makeText(RegisterActivity.this, "���䲻��Ϊ��!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                User user = new User();

                user.setLoginname(loginname.getText().toString());

                user.setLoginpsw(loginpsw.getText().toString());

                user.setEmail(email.getText().toString());

                user.setTel(tel.getText().toString());

                if (us.isExist(user)) {
                    Toast.makeText(RegisterActivity.this, "���˺��Ѵ��ڣ�����������!",
                            Toast.LENGTH_LONG).show();
                    return;
                } else {
                    us.save(user);

                    Toast.makeText(RegisterActivity.this, "ע��ɹ�!",
                            Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });

    }

}
