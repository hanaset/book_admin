package com.example.jeongbin.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    private EditText id, passwd;
    public static String login_id, login_factory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkUtil.setNetworkPolicy();


        Button join_btn = (Button)findViewById(R.id.main_join_btn); // 회원 가입 버튼
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join_intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(join_intent);
            }
        });

        Button login_btn = (Button)findViewById(R.id.main_login_btn); // 로그인 버튼
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    id = (EditText)findViewById(R.id.main_id_edit);
                    passwd = (EditText)findViewById(R.id.main_passwd_edit);
                    PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/login.php");
                    String result = request.PhPjoin(String.valueOf(id.getText()),String.valueOf(passwd.getText()));
                    if(result.equals("-1")){
                        Toast.makeText(getApplication(), "로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        login_factory = result;
                        Toast.makeText(getApplication(), login_factory + " 환영합니다.",Toast.LENGTH_SHORT).show();
                        Intent login_intent = new Intent(MainActivity.this, LoginActivity.class);
                        login_intent.putExtra("factory",login_factory);
                        login_intent.putExtra("id",String.valueOf(id.getText()));
                        startActivity(login_intent);
                    }

                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });

    }


}
