package com.example.jeongbin.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by JeongBin on 2016-11-10.
 */

public class BookinfoActivity extends AppCompatActivity {

    String id,book_name, author, publisher, img_url, num, loan_name, loan_date, return_date, phone, state_num, state;
    Button messge_btn, hidden_btn, del_btn;
    TextView book_name_tv, author_tv, publisher_tv, name_tv, time_tv, phone_tv, num_tv;

    JSONArray text;

    public static Handler handler = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        Intent pre_intent = getIntent();
        id = pre_intent.getExtras().getString("id");
        book_name = pre_intent.getExtras().getString("book_name");
        num = pre_intent.getExtras().getString("num");
        state_num = pre_intent.getExtras().getString("state_num");

        if(state_num.equals("1"))
            state = "대출 중";
        else if(state_num.equals("2"))
            state = "대출 대기 중";
        else if(state_num.equals("3"))
            state = "반납 대기 중";
        else
            state = "보유 중";

        setTitle(state);

        try{
            PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/book/book_info.php");
            String result = request.PhPbook(id,book_name,num);

            try{
                JSONObject jsonObject = new JSONObject(result);
                text = jsonObject.getJSONArray("result");

                JSONObject c = text.getJSONObject(0);
                author = c.getString("author");
                publisher = c.getString("publisher");
                loan_name = c.getString("name");
                phone = c.getString("phone");
                loan_date = c.getString("loan_date");
                return_date = c.getString("return_date");
                img_url = c.getString("image");

                messge_btn = (Button)findViewById(R.id.info_call_btn);
                hidden_btn = (Button)findViewById(R.id.info_hidden_btn);
                del_btn = (Button)findViewById(R.id.info_book_del_btn);

                book_name_tv = (TextView)findViewById(R.id.info_book_name);
                author_tv = (TextView)findViewById(R.id.info_author);
                publisher_tv = (TextView)findViewById(R.id.info_publisher);
                num_tv = (TextView)findViewById(R.id.info_book_num);
                name_tv = (TextView)findViewById(R.id.info_loan_name);
                time_tv = (TextView)findViewById(R.id.info_time);
                phone_tv = (TextView)findViewById(R.id.info_phone);

                book_name_tv.setText(book_name);
                author_tv.setText(author);
                publisher_tv.setText(publisher);
                num_tv.setText(num);

                if(state_num.equals("1")){
                    name_tv.setText(loan_name);
                    time_tv.setText(loan_date + " ~ " + return_date);
                    phone_tv.setText(phone);
                    hidden_btn.setText("뒤로 가기");

                }else if(state_num.equals("2") || state_num.equals("3")){
                    name_tv.setText(loan_name);
                    time_tv.setText(loan_date + " ~ " + return_date);
                    phone_tv.setText(phone);
                    hidden_btn.setText("요청 수락");

                }else{
                    name_tv.setText("대출 중이 아닙니다.");
                    time_tv.setText("대출 중이 아닙니다.");
                    phone_tv.setText("대출 중이 아닙니다.");
                    hidden_btn.setText("뒤로 가기");
                }

                new DownloadImageTask((ImageView)findViewById(R.id.info_image)).execute(img_url);

                messge_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(state_num.equals("1") ){
                            Messenger messenger = new Messenger(getApplicationContext());
                            messenger.sendMessageTo(phone,loan_name+"님의 반납 날짜는 "+return_date+" 입니다.");
                        }else if(state_num.equals("4")){
                            Messenger messenger = new Messenger(getApplicationContext());
                            messenger.sendMessageTo(phone,"반납 날짜가 지났습니다. 빠른 반납 바랍니다.");
                        }
                        else{
                            Toast.makeText(getApplication(),"대출 또는 연체 중이 아닙니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                hidden_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(state_num.equals("1")){
                            finish();
                        }else if(state_num.equals("2") || state_num.equals("3")){
                            try{
                                PHPRequest request1 = new PHPRequest("http://114.70.93.130/book_admin/login/book/book_state_modity.php");
                                String result1 = request1.PhPbook(id, book_name, num, state_num);

                                if(result1.equals("1")) {
                                    Toast.makeText(getApplication(), "성공적으로 처리되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else
                                    Toast.makeText(getApplication(),"요청 처리에 실패하였습니다.",Toast.LENGTH_SHORT).show();

                            }catch(MalformedURLException e){
                                e.printStackTrace();
                            }
                        }else{
                            finish();
                        }
                    }
                });

                del_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            PHPRequest request2 = new PHPRequest("http://114.70.93.130/book_admin/login/book/book_del.php");
                            String result2 = request2.PhPbook(id, book_name, num);

                            if(result2.equals("1")){
                                Toast.makeText(getApplication(), "책을 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplication(),"책을 삭제하지 못하였습니다.",Toast.LENGTH_SHORT).show();;
                            }
                        }catch (MalformedURLException e){
                            e.printStackTrace();
                        }
                    }
                });


            }catch(JSONException e){
                e.printStackTrace();
            }

        }catch(MalformedURLException e){
            e.printStackTrace();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
