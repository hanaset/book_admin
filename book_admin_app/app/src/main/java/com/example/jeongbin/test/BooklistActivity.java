package com.example.jeongbin.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
        final String user = id;

        bookList = new ArrayList<>();

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
                    String state_num = c.getString("state_num");
                    String num = c.getString("num");

                    HashMap<String, String> books = new HashMap<>();

                    books.put("book_name", book_name);
                    books.put("author", author);
                    books.put("publisher", publisher);
                    books.put("num", num);

                    String state;

                    if(state_num.equals("1"))
                        state = "대출 중";
                    else if(state_num.equals("2"))
                        state = "대출 대기 중";
                    else if(state_num.equals("3"))
                        state = "반납 대기 중";
                    else if(state_num.equals("4"))
                        state = "연체 중";
                    else
                        state = "보유 중";

                    books.put("state",state);
                    books.put("state_num",state_num);
                    bookList.add(books);
                }

                if(bookList.size() == 0)
                {
                    setContentView(R.layout.activity_notpage);
                }
                else {
                    ListAdapter adapter = new SimpleAdapter(BooklistActivity.this, bookList, R.layout.item_book_list,
                            new String[]{"book_name","author","publisher","state"},
                            new int[]{R.id.book_list_name, R.id.book_list_author, R.id.book_list_publisher, R.id.book_list_state});

                    list = (ListView)findViewById(R.id.book_list_listview);
                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, String> books = bookList.get(position);
                            Intent item_intent = new Intent(BooklistActivity.this, BookinfoActivity.class);
                            item_intent.putExtra("id",user);
                            item_intent.putExtra("book_name",books.get("book_name"));
                            item_intent.putExtra("num",books.get("num"));
                            item_intent.putExtra("state_num",books.get("state_num"));
                            startActivity(item_intent);
                        }
                    });
                }



            }catch(JSONException e){
                e.printStackTrace();
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }


    }
}
