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
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by JeongBin on 2016-11-09.
 */

public class BookaddActivity extends AppCompatActivity {
    String book_url;
    String search_url = "http://book.naver.com/search/search.nhn?sm=sta_hty.book&sug=&where=nexearch&query=";
    String book_name, author, publishter, content, img_url;

    public static Handler handler = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);
        NetworkUtil.setNetworkPolicy();

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

                img_url = links.attr("contetnt");


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

