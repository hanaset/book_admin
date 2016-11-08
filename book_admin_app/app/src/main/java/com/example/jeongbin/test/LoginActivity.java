package com.example.jeongbin.test;

/**
 * Created by JeongBin on 2016-11-02.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.MalformedURLException;

public class LoginActivity extends AppCompatActivity {
    String id, factory;
    String ISBN = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(MainActivity.login_factory + " 님 환영합니다.");
        setContentView(R.layout.activity_login);

        TextView loan_num = (TextView)findViewById(R.id.login_loan_view);
        TextView loan_call_num = (TextView)findViewById(R.id.login_loan_call_view);
        TextView return_num = (TextView)findViewById(R.id.login_return_view);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        factory = intent.getExtras().getString("factory");

        try {
            PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/login_main.php");
            String result = request.PhPjoin(id);
            String[] book_num = result.split(",");

            if(book_num[0].equals("-1")){
                loan_num.setText("현재 없습니다.");
            }
            else{
                loan_num.setText(book_num[0]+" 개가 있습니다.");
            }

            if(book_num[1].equals("-1")){
                loan_call_num.setText("현재 없습니다.");
            }
            else{
                loan_call_num.setText(book_num[1]+" 개가 있습니다.");
            }

            if(book_num[2].equals("-1")){
                return_num.setText("현재 없습니다.");
            }
            else{
                return_num.setText(book_num[2]+" 개가 있습니다.");
            }

            Button book_add_btn = (Button)findViewById(R.id.login_book_add_btn);
            final Activity activity = this;
            book_add_btn.setOnClickListener(new View.OnClickListener() {
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

        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

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
                Intent book_add_intent = new Intent(LoginActivity.this, BookaddActivity.class);
                book_add_intent.putExtra("ISBN", ISBN);
                startActivity(book_add_intent);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}