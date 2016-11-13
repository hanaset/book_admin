package com.example.jeongbin.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;

/**
 * Created by JeongBin on 2016-11-12.
 */

public class InfomodityActivity extends AppCompatActivity {

    private EditText id, passwd;
    int check = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        NetworkUtil.setNetworkPolicy();

        final Button passwd_btn = (Button)findViewById(R.id.myinfo_passwd_btn);
        Button factory_btn = (Button)findViewById(R.id.myinfo_factory_btn);

        Button confirm_btn = (Button)findViewById(R.id.myinfo_confrim_btn);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = (EditText)findViewById(R.id.myinfo_id_confirm);
                passwd = (EditText)findViewById(R.id.myinfo_passwd_confirm);
                try {
                    PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/login.php");
                    String result = request.PhPjoin(String.valueOf(id.getText()), String.valueOf(passwd.getText()));
                    if(result.equals("-1")){
                        Toast.makeText(getApplication(), "아이디와 비밀번호를 다시 입력해주세요.",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(),"확인 완료 되었습니다.",Toast.LENGTH_SHORT).show();
                        check = 1;
                    }
                }catch(MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });

        passwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check == 1) {
                    Intent intent = new Intent(InfomodityActivity.this, ModitiyActivity.class);
                    intent.putExtra("id", String.valueOf(id.getText()));
                    intent.putExtra("mode","1");
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplication(),"아이디와 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        factory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check == 1) {
                    Intent intent = new Intent(InfomodityActivity.this, ModitiyActivity.class);
                    intent.putExtra("id", String.valueOf(id.getText()));
                    intent.putExtra("mode","2");
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplication(),"아이디와 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
