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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JeongBin on 2016-11-13.
 */

public class ReturnActivity extends AppCompatActivity {

    String user, ISBN;
    JSONArray book = null;
    String num;
    ArrayList<HashMap<String, String>> bookList;
    String img_url;

    ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        Intent intent = getIntent();
        user = intent.getExtras().getString("id");
        ISBN = intent.getExtras().getString("ISBN");

        bookList = new ArrayList<>();

        TextView book_text = (TextView)findViewById(R.id.return_book_name);

        try{
            PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/return.php");
            String result = request.PhPbook(user, ISBN);

            if(result.equals("-1")){
                Toast.makeText(getApplication(),"대출 중인 책이 아닙니다.",Toast.LENGTH_SHORT).show();
                finish();
            }else {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    book = jsonObject.getJSONArray("result");

                    for (int i = 0; i < book.length(); i++) {
                        JSONObject c = book.getJSONObject(i);
                        String book_name = c.getString("book_name");
                        String name = c.getString("name");
                        num = c.getString("num");
                        String phone = c.getString("phone");
                        String image = c.getString("image");

                        HashMap<String, String> books = new HashMap<>();

                        books.put("book_name", book_name);
                        books.put("name", name);
                        books.put("phone", phone);
                        books.put("num", num);

                        if (i == 0) {
                            book_text.setText(book_name);
                            img_url = image;
                        }

                        bookList.add(books);
                    }


                    new ReturnActivity.DownloadImageTask((ImageView) findViewById(R.id.return_book_image)).execute(img_url);

                    ListAdapter adapter = new SimpleAdapter(ReturnActivity.this, bookList, R.layout.return_book_list,
                            new String[]{"num", "name", "phone"},
                            new int[]{R.id.return_item_num, R.id.return_item_name, R.id.return_item_phone});

                    list = (ListView) findViewById(R.id.return_listview);
                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, String> books = bookList.get(position);

                            try {
                                PHPRequest request1 = new PHPRequest("http://114.70.93.130/book_admin/login/book/return_confirm.php");
                                String result = request1.PhPreturn(user, ISBN, books.get("num"));

                                if (result.equals("1")) {
                                    Toast.makeText(getApplication(), "성공적으로 반납되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplication(), "반납에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                }

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }catch (MalformedURLException e){
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
