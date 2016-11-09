package com.example.jeongbin.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JeongBin on 2016-11-09.
 */

public class BookListAdapter extends BaseAdapter {
    private ArrayList<BookItem> listData;
    private  LayoutInflater layoutInflater;

    public BookListAdapter(Context aContext, ArrayList<BookItem> listData){
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_book_list, null);
            holder = new ViewHolder();
            holder.book_name = (TextView)convertView.findViewById(R.id.book_list_name);
            holder.author = (TextView)convertView.findViewById(R.id.book_list_author);
            holder.publisher = (TextView)convertView.findViewById(R.id.book_list_publisher);
            holder.state = (TextView)convertView.findViewById(R.id.book_list_state);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.book_name.setText(listData.get(position).getBook_name());
        holder.author.setText(listData.get(position).getAuthor());
        holder.publisher.setText(listData.get(position).getPublisher());
        holder.state.setText(listData.get(position).getState());

        return convertView;
    }

    static class ViewHolder{
        TextView book_name;
        TextView author;
        TextView publisher;
        TextView state;
    }
}

