package com.example.jeongbin.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by JeongBin on 2016-10-30.
 */

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("도서 검색");
        setContentView(R.layout.activity_search);
    }
}
