package com.example.jeongbin.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by JeongBin on 2016-11-13.
 */

public class LoanActivity extends AppCompatActivity {

    String user, ISBN;
    String num;
    String img_url;
    JSONArray book = null;

    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        Intent intent = getIntent();
        user = intent.getExtras().getString("id");
        ISBN = intent.getExtras().getString("ISBN");
        final EditText name_edit = (EditText)findViewById(R.id.loan_name_edit);
        final EditText phone_eidt = (EditText)findViewById(R.id.loan_phone_edit);
        button  = (Button)findViewById(R.id.loan_btn);


        try{
            PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/book/loan.php");
            String result = request.PhPbook(user, ISBN);

            if(result.equals("-1")){
                Toast.makeText(getApplication(),"현재 대출 할 수 없는 책입니다.",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    book = jsonObject.getJSONArray("result");

                    JSONObject c = book.getJSONObject(0);
                    ((TextView)findViewById(R.id.loan_book_name)).setText(c.getString("book_name"));
                    img_url = c.getString("image");
                    num = c.getString("num");

                    new LoanActivity.DownloadImageTask((ImageView) findViewById(R.id.loan_book_image)).execute(img_url);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name_edit.length() == 0 || phone_eidt.length() == 0){
                    Toast.makeText(getApplication(),"입력 란을 모두 적어주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        PHPRequest request1 = new PHPRequest("http://114.70.93.130/book_admin/login/book/loan_confirm.php");
                        String result1 = request1.PhPloan(user,ISBN, num, String.valueOf(name_edit.getText()),String.valueOf(phone_eidt.getText()));

                        if(result1.equals("1")){
                            Toast.makeText(getApplication(),"대출 되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplication(),"대출에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }catch (MalformedURLException e){
                        e.printStackTrace();
                    }
                }
            }
        });
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
