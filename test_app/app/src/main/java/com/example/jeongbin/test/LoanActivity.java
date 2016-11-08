package com.example.jeongbin.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by JeongBin on 2016-10-31.
 */

public class LoanActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("도서 대출");
        setContentView(R.layout.activity_loan);
    }
}
