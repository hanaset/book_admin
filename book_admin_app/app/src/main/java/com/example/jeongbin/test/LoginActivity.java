package com.example.jeongbin.test;

/**
 * Created by JeongBin on 2016-11-02.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;

public class LoginActivity extends AppCompatActivity {
    String id, factory;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(MainActivity.login_factory + " 님 환영합니다.");
        setContentView(R.layout.activity_login);

        TextView loan_num = (TextView)findViewById(R.id.login_loan_view);
        TextView loan_call_num = (TextView)findViewById(R.id.login_loan_call_view);
        TextView return_num = (TextView)findViewById(R.id.login_return_view);
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

            if(book_num[3].equals("-1")){
                late_num.setText("현재 없습니다.");
            }
            else{
                late_num.setText(book_num[3]+" 개가 있습니다.");
            }

            Button book_add_btn = (Button)findViewById(R.id.login_book_admin_btn);
            book_add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent admin_intent = new Intent(LoginActivity.this, BookadminActivity.class);
                    admin_intent.putExtra("id",id);
                    admin_intent.putExtra("factory",factory);
                    startActivity(admin_intent);
                }
            });

        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }


}