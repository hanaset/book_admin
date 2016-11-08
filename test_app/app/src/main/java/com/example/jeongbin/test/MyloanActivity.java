package com.example.jeongbin.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by JeongBin on 2016-10-31.
 */

public class MyloanActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("나의 대출 현황");
        setContentView(R.layout.activity_myloan);
    }
}
