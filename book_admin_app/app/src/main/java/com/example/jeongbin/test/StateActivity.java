package com.example.jeongbin.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JeongBin on 2016-11-13.
 */

public class StateActivity extends AppCompatActivity {

    String user;
    JSONArray book = null;
    ArrayList<HashMap<String, String>> bookList;

    ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        Intent intent = getIntent();
        user = intent.getExtras().getString("id");
        Button loan_btn = (Button)findViewById(R.id.state_loan_btn);
        Button late_btn = (Button)findViewById(R.id.state_late_btn);
        final TextView textView = (TextView)findViewById(R.id.state_text);

        bookList = new ArrayList<>();

        try{
            PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/state.php");
            String result = request.PhPbook(user, "0");

            if(result.equals("-1")){
                Toast.makeText(getApplication(),"대출 또는 연체 목록이 없습니다.",Toast.LENGTH_SHORT).show();
            }else{
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    book = jsonObject.getJSONArray("result");

                    for(int i = 0; i<book.length(); i++){
                        JSONObject c = book.getJSONObject(i);
                        String book_name = c.getString("book_name");
                        String name = c.getString("name");
                        String date = c.getString("loan_date") + " ~ " + c.getString("return_date");
                        String num = c.getString("num");
                        String state_num = c.getString("state_num");

                        String state = null;

                        if(state_num.equals("1")){
                            state = "대출 중";
                        }else if(state_num.equals("2")){
                            state = "연체 중";
                        }

                        HashMap<String, String> books = new HashMap<>();

                        books.put("book_name",book_name);
                        books.put("name",name);
                        books.put("date",date);
                        books.put("num",num);
                        books.put("state",state);
                        books.put("state_num",state_num);

                        bookList.add(books);

                    }

                    ListAdapter adapter = new SimpleAdapter(StateActivity.this, bookList, R.layout.item_state,
                            new String[]{"name","book_name","date","state"},
                            new int[]{R.id.i_state_name,R.id.i_state_book, R.id.i_state_loan, R.id.i_state_state});

                    list = (ListView)findViewById(R.id.state_listview);
                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, String> books = bookList.get(position);
                            Intent item_intent = new Intent(StateActivity.this, BookinfoActivity.class);
                            item_intent.putExtra("id",user);
                            item_intent.putExtra("book_name",books.get("book_name"));
                            item_intent.putExtra("num",books.get("num"));
                            item_intent.putExtra("state_num",books.get("state_num"));
                            startActivity(item_intent);
                        }
                    });

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        loan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/state.php");
                    String result = request.PhPbook(user, "1");
                    textView.setText("대출 현황");

                    if(result.equals("-1")){
                        Toast.makeText(getApplication(),"대출 또는 연체 목록이 없습니다.",Toast.LENGTH_SHORT).show();
                    }else{
                        try{
                            JSONObject jsonObject = new JSONObject(result);
                            book = jsonObject.getJSONArray("result");

                            for(int i = 0; i<book.length(); i++){
                                JSONObject c = book.getJSONObject(i);
                                String book_name = c.getString("book_name");
                                String name = c.getString("name");
                                String date = c.getString("loan_date") + " ~ " + c.getString("return_date");
                                String num = c.getString("num");
                                String state_num = c.getString("state_num");

                                String state = null;

                                if(state_num.equals("1")){
                                    state = "대출 중";
                                }else if(state_num.equals("2")){
                                    state = "연체 중";
                                }

                                HashMap<String, String> books = new HashMap<>();

                                books.put("book_name",book_name);
                                books.put("name",name);
                                books.put("date",date);
                                books.put("num",num);
                                books.put("state",state);
                                books.put("state_num",state_num);

                                bookList.add(books);

                            }

                            ListAdapter adapter = new SimpleAdapter(StateActivity.this, bookList, R.layout.item_state,
                                    new String[]{"name","book_name","date","state"},
                                    new int[]{R.id.i_state_name,R.id.i_state_book, R.id.i_state_loan, R.id.i_state_state});

                            list = (ListView)findViewById(R.id.state_listview);
                            list.setAdapter(adapter);

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    HashMap<String, String> books = bookList.get(position);
                                    Intent item_intent = new Intent(StateActivity.this, BookinfoActivity.class);
                                    item_intent.putExtra("id",user);
                                    item_intent.putExtra("book_name",books.get("book_name"));
                                    item_intent.putExtra("num",books.get("num"));
                                    item_intent.putExtra("state_num",books.get("state_num"));
                                    startActivity(item_intent);
                                }
                            });

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });

        late_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    PHPRequest request = new PHPRequest("http://114.70.93.130/book_admin/login/state.php");
                    String result = request.PhPbook(user, "2");
                    textView.setText("연체 현황");

                    if(result.equals("-1")){
                        Toast.makeText(getApplication(),"대출 또는 연체 목록이 없습니다.",Toast.LENGTH_SHORT).show();
                    }else{
                        try{
                            JSONObject jsonObject = new JSONObject(result);
                            book = jsonObject.getJSONArray("result");

                            for(int i = 0; i<book.length(); i++){
                                JSONObject c = book.getJSONObject(i);
                                String book_name = c.getString("book_name");
                                String name = c.getString("name");
                                String date = c.getString("loan_date") + " ~ " + c.getString("return_date");
                                String num = c.getString("num");
                                String state_num = c.getString("state_num");

                                String state = null;

                                if(state_num.equals("1")){
                                    state = "대출 중";
                                }else if(state_num.equals("2")){
                                    state = "연체 중";
                                }

                                HashMap<String, String> books = new HashMap<>();

                                books.put("book_name",book_name);
                                books.put("name",name);
                                books.put("date",date);
                                books.put("num",num);
                                books.put("state",state);
                                books.put("state_num",state_num);

                                bookList.add(books);

                            }

                            ListAdapter adapter = new SimpleAdapter(StateActivity.this, bookList, R.layout.item_state,
                                    new String[]{"name","book_name","date","state"},
                                    new int[]{R.id.i_state_name,R.id.i_state_book, R.id.i_state_loan, R.id.i_state_state});

                            list = (ListView)findViewById(R.id.state_listview);
                            list.setAdapter(adapter);

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    HashMap<String, String> books = bookList.get(position);
                                    Intent item_intent = new Intent(StateActivity.this, BookinfoActivity.class);
                                    item_intent.putExtra("id",user);
                                    item_intent.putExtra("book_name",books.get("book_name"));
                                    item_intent.putExtra("num",books.get("num"));
                                    item_intent.putExtra("state_num",books.get("state_num"));
                                    startActivity(item_intent);
                                }
                            });

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
