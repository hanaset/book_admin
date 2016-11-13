package com.example.jeongbin.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JeongBin on 2016-11-10.
 */

public class SearchActivity extends AppCompatActivity {
    String name;
    String kind;
    String myJSON;
    String id, user;

    JSONArray book = null;
    ArrayList<HashMap<String, String>> bookList;

    ListView list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final ArrayList<String> arrayList = new ArrayList<String>();
        final Button button = (Button)findViewById(R.id.search_search_btn);
        final EditText content = (EditText)findViewById(R.id.search_edit_name);
        arrayList.add("책 이름");
        arrayList.add("저자");
        arrayList.add("출판사");
        arrayList.add("대출자");

        Intent pre_intent = getIntent();
        id = pre_intent.getExtras().getString("id");
        user = id;

        bookList = new ArrayList<>();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        final Spinner sp = (Spinner)this.findViewById(R.id.search_spinner);
        sp.setAdapter(adapter1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kind = sp.getSelectedItem().toString();
                name = content.getText().toString();
                try{
                    PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/book/book_search.php");
                    String result = request.PhPsearch(id,name, kind);

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
                                state = "연체 중";
                            else
                                state = "보유 중";

                            books.put("state",state);
                            books.put("state_num",state_num);
                            bookList.add(books);
                        }

                        if(book.length() == 0 || bookList.size() == 0)
                        {
                            Toast.makeText(getApplication(),"검색한 내용의 책이 없습니다.",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ListAdapter adapter2 = new SimpleAdapter(SearchActivity.this, bookList, R.layout.item_book_list,
                                    new String[]{"book_name","author","publisher","state"},
                                    new int[]{R.id.book_list_name, R.id.book_list_author, R.id.book_list_publisher, R.id.book_list_state});

                            list = (ListView)findViewById(R.id.book_search_listview);

                            list.setAdapter(adapter2);

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    HashMap<String, String> books = bookList.get(position);
                                    Intent item_intent = new Intent(SearchActivity.this, BookinfoActivity.class);
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

                }catch(MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });

    }
}
