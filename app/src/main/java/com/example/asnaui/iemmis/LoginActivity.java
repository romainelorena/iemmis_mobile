package com.example.asnaui.iemmis;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.asnaui.iemmis.Helper.ApiRequest;
import com.example.asnaui.iemmis.Helper.DBContext;
import com.example.asnaui.iemmis.Interfaces.LoginInterface;
import com.example.asnaui.iemmis.Model.User;
import com.example.asnaui.iemmis.Presenter.LoginPresenter;

/**
 * Created by Asnaui on 1/15/2018.
 */

public class LoginActivity extends AppCompatActivity implements LoginInterface.LoginView {
    Button mBtn_login;
    ImageView mImageView_loader;
    ProgressDialog progressDialog;
    DBContext dbContext;
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbContext = new DBContext(this);
        mBtn_login = findViewById(R.id.login_button);
        mImageView_loader = findViewById(R.id.loader);
        loginPresenter = new LoginPresenter(this, Volley.newRequestQueue(this));
        mBtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setMessage("Logging in....");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        loginPresenter.login("0618","1234");
                    }
                });
                thread.start();
            }
        });
    }

    @Override
    public void showFailMessage() {
        progressDialog.dismiss();
    }

    @Override
    public void navigateToHome(User user) {
        progressDialog.dismiss();
        dbContext.insertUser(user);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}

