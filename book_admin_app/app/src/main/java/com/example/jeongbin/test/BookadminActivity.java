package com.example.jeongbin.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by JeongBin on 2016-11-09.
 */

public class BookadminActivity extends AppCompatActivity {

    String ISBN;
    String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_admin);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");

        Button book_add = (Button)findViewById(R.id.admin_add_btn);
        final Activity activity = this;
        book_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        Button book_list = (Button)findViewById(R.id.admin_list_btn);
        book_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent book_list_intent = new Intent(BookadminActivity.this, BooklistActivity.class);
                book_list_intent.putExtra("id",id);
                startActivity(book_list_intent);
            }
        });

        Button book_search = (Button)findViewById(R.id.admin_search_btn);
        book_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search_intent = new Intent(BookadminActivity.this, SearchActivity.class);
                search_intent.putExtra("id",id);
                startActivity(search_intent);
            }
        });
    }
    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Log.d("LoginActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }else{
                Log.d("LoginActivity", "Scanned");
                ISBN = result.getContents();
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                Intent book_add_intent = new Intent(BookadminActivity.this, BookaddActivity.class);
                book_add_intent.putExtra("ISBN", ISBN);
                book_add_intent.putExtra("id", id);
                startActivity(book_add_intent);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
