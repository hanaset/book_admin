package com.example.jeongbin.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JeongBin on 2016-11-09.
 */

public class BooklistActivity extends AppCompatActivity {

    String myJSON;
    String id;

    JSONArray book = null;
    ArrayList<HashMap<String, String>> bookList;

    ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        Intent pre_intent = getIntent();
        id = pre_intent.getExtras().getString("id");

        bookList = new ArrayList<HashMap<String,String>>();

        try{
            PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/book/book_list.php");
            String result = request.PhPjoin(id);

            try {
                JSONObject jsonObject = new JSONObject(result);
                book = jsonObject.getJSONArray("result");

                for(int i = 0 ; i<book.length(); i++){
                    JSONObject c = book.getJSONObject(i);
                    String book_name = c.getString("book_name");
                    String author = c.getString("author");
                    String publisher = c.getString("publisher");
                    String loan = c.getString("loan");
                    String loan_call = c.getString("loan_call");
                    String return_call = c.getString("return_call");

                    HashMap<String, String> books = new HashMap<String, String>();

                    books.put("book_name", book_name);
                    books.put("author", author);
                    books.put("publisher", publisher);

                    String state;

                    if(loan.equals("1"))
                        state = "대출 중";
                    else if(loan_call.equals("1"))
                        state = "대출 대기 중";
                    else if(return_call.equals("1"))
                        state = "반납 대기 중";
                    else
                        state = "보유 중";

                    books.put("state",state);
                    bookList.add(books);
                }

                ListAdapter adapter = new SimpleAdapter(BooklistActivity.this, bookList, R.layout.item_book_list,
                        new String[]{"book_name","author","publisher","state"},
                        new int[]{R.id.book_list_name, R.id.book_list_author, R.id.book_list_publisher, R.id.book_list_state});

                list = (ListView)findViewById(R.id.book_list_listview);
                list.setAdapter(adapter);

            }catch(JSONException e){
                e.printStackTrace();
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }


    }
}
