package com.example.jeongbin.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;

/**
 * Created by JeongBin on 2016-10-31.
 */

public class JoinActivity extends AppCompatActivity {
    private EditText id, passwd, factory;
    int id_check = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("관리자 회원가입");
        setContentView(R.layout.activity_join);
        NetworkUtil.setNetworkPolicy();
        id = (EditText)findViewById(R.id.join_id_edit);
        passwd = (EditText)findViewById(R.id.join_passwd_edit);
        factory = (EditText)findViewById(R.id.join_factory_edit);

        Button join_btn = (Button)findViewById(R.id.join_join_btn);
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(id_check != 1) {
                        Toast.makeText(getApplication(),"아이디 중복확인을 해주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    PHPRequest request = new PHPRequest("http://114.70.93.130/test/join/join.php");
                    String result = request.PhPjoin(String.valueOf(id.getText()),String.valueOf(passwd.getText()),String.valueOf(factory.getText()));
                    if(result.equals("1")){
                        Toast.makeText(getApplication(),"회원가입 성공",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getApplication(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                    }
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });

        Button id_confirm = (Button)findViewById(R.id.join_join_confirm);
        id_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(id.length() <= 0)
                    {
                        Toast.makeText(JoinActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    PHPRequest request = new PHPRequest("http://114.70.93.130/test/join/join_id_confirm.php");
                    String result = request.PhPjoin(String.valueOf(id.getText()));
                    if(result.equals("1")){
                        Toast.makeText(getApplication(), "아이디가 사용가능합니다.", Toast.LENGTH_SHORT).show();
                        id_check = 1;
                    }
                    else{
                        Toast.makeText(getApplication(), "아이디가 중복입니다", Toast.LENGTH_SHORT).show();
                    }

                }catch(MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
