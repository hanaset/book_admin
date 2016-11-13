package com.example.jeongbin.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;

/**
 * Created by JeongBin on 2016-11-12.
 */

public class ModitiyActivity extends AppCompatActivity {

    String id;
    String mode;
    String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_modity);

        Intent intent = getIntent();
        id =intent.getExtras().getString("id");
        mode = intent.getExtras().getString("mode");

        TextView text = (TextView)findViewById(R.id.modity_text);
        final EditText editText = (EditText)findViewById(R.id.moditiy_edit);
        Button button = (Button)findViewById(R.id.modity_btn);

        if(mode.equals("1")){
            text.setText("변경 할 비밀번호를 입력하세요");
        }
        else if(mode.equals("2")){
            text.setText("변경 할 기관명을 입력하세요");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content= String.valueOf(editText.getText());
                if(content.length() == 0){
                    Toast.makeText(getApplicationContext(),"변경 할 내용을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/modity.php");
                        String result = request.PhPmodity(id,mode,content);

                        if(result.equals("1")){
                            Toast.makeText(getApplication(),"성공적으로 변경되었습니다.",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplication(),"변경에 대해 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }catch(MalformedURLException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
