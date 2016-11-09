package com.example.jeongbin.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by JeongBin on 2016-11-09.
 */

public class BookaddActivity extends AppCompatActivity {
    String book_url;
    String search_url = "http://book.naver.com/search/search.nhn?sm=sta_hty.book&sug=&where=nexearch&query=";
    String book_name, author, publishter, content, img_url;
    String id, ISBN;

    public static Handler handler = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);
        NetworkUtil.setNetworkPolicy();

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        ISBN = intent.getExtras().getString("ISBN");

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0){
                    TextView name_text = (TextView)findViewById(R.id.add_name);
                    TextView author_text = (TextView)findViewById(R.id.add_author);
                    TextView publisher_text = (TextView)findViewById(R.id.add_publisher);
                    TextView content_text = (TextView)findViewById(R.id.add_content);

                    name_text.setText(book_name);
                    author_text.setText(author);
                    publisher_text.setText(publishter);
                    content_text.setText(content);

                    new DownloadImageTask((ImageView)findViewById(R.id.add_book_image)).execute(img_url);

                }
            }
        };


        Intent pre_intent = getIntent();
        search_url += pre_intent.getExtras().getString("ISBN");
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

        Button add = (Button)findViewById(R.id.add_confirm);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/book/book_add.php");
                    String result = request.Phpjoin(id, book_name, author, publishter, content, img_url, ISBN);
                    if(result.equals("-1")){
                        Toast.makeText(getApplication(), "책 추가에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(), "책을 추가하였습니다.",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }catch (MalformedURLException e){
                    e.printStackTrace();
                }

            }
        });

        Button cancel = (Button)findViewById(R.id.add_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

////////////////////////////////////////////////////////////////////////
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(search_url).get();
                Elements links = doc.select("[href*=http://book.naver.com/bookdb/book_detail]");

                book_url = links.attr("href");

                doc = Jsoup.connect(book_url).get();
                links = doc.select("[property*=og:title]");

                book_name = links.attr("content");

                doc = Jsoup.connect(book_url).get();
                links = doc.select("[class*=N=a:bil.publisher]");

                publishter = links.text();

                doc = Jsoup.connect(book_url).get();
                links = doc.select("[class*=N=a:bil.author]");

                author = links.text();

                doc = Jsoup.connect(book_url).get();
                links = doc.select("[property*=:description]");

                content = links.attr("content");

                doc = Jsoup.connect(book_url).get();
                links = doc.select("[property*=og:image]");

                img_url = links.attr("content");


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            handler.sendEmptyMessage(0);
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

