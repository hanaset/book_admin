package com.example.jeongbin.test;

/**
 * Created by JeongBin on 2016-11-02.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(MainActivity.login_factory + " 님 환영합니다.");
        setContentView(R.layout.activity_login);
    }
}