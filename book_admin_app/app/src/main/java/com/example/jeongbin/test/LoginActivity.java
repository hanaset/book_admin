package com.example.jeongbin.test;

/**
 * Created by JeongBin on 2016-11-02.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.MalformedURLException;

public class LoginActivity extends AppCompatActivity {
    String id, factory;
    String ISBN;

    int state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(MainActivity.login_factory + " 님 환영합니다.");
        setContentView(R.layout.activity_login);

        TextView loan_num = (TextView)findViewById(R.id.login_loan_view);
        TextView late_num = (TextView)findViewById(R.id.login_late_view);

        final Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        factory = intent.getExtras().getString("factory");

        try{
            PHPRequest date_check = new PHPRequest("http://114.70.93.130/book_admin/date_check.php");
            String check_result = date_check.PhPjoin(id);

            if(check_result.equals("-1")){
                Toast.makeText(getApplication(),"책 갱신에 실패하였습니다.",Toast.LENGTH_SHORT).show();
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

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
                late_num.setText("현재 없습니다.");
            }
            else{
                late_num.setText(book_num[1]+" 개가 있습니다.");
            }

            Button state_btn = (Button)findViewById(R.id.login_state_btn);
            state_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent state_intent = new Intent(LoginActivity.this, StateActivity.class);
                    state_intent.putExtra("id",id);
                    startActivity(state_intent);
                }
            });


            Button book_admin_btn = (Button)findViewById(R.id.login_book_admin_btn);
            book_admin_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent admin_intent = new Intent(LoginActivity.this, BookadminActivity.class);
                    admin_intent.putExtra("id",id);
                    admin_intent.putExtra("factory",factory);
                    startActivity(admin_intent);
                }
            });

            Button loan_btn = (Button)findViewById(R.id.login_loan_btn);
            final Activity activity = this;
            loan_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    state = 1;

                    IntentIntegrator integrator = new IntentIntegrator(activity);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("Scan");
                    integrator.setCameraId(0);
                    integrator.setBeepEnabled(false);
                    integrator.setBarcodeImageEnabled(false);
                    integrator.initiateScan();
                }
            });

            Button return_btn =(Button)findViewById(R.id.login_return_btn);
            return_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    state = 2;

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


    ////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Log.d("LoginActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }else{

                if(state == 1) {

                    Log.d("LoginActivity", "Scanned");
                    ISBN = result.getContents();
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                    Intent book_intent = new Intent(LoginActivity.this, LoanActivity.class);
                    book_intent.putExtra("ISBN", ISBN);
                    book_intent.putExtra("id", id);
                    startActivity(book_intent);
                }else if(state == 2){

                    Log.d("LoginActivity", "Scanned");
                    ISBN = result.getContents();
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                    Intent book_intent = new Intent(LoginActivity.this, ReturnActivity.class);
                    book_intent.putExtra("ISBN", ISBN);
                    book_intent.putExtra("id", id);
                    startActivity(book_intent);
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}