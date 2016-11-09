package com.example.jeongbin.test;

/**
 * Created by JeongBin on 2016-11-09.
 */

public class BookItem {
    private String book_name;
    private String author;
    private String publisher;
    private String state;

    public String getBook_name() {
        return book_name;
    }

    public String getAuthor(){
        return author;
    }

    public String getPublisher(){
        return publisher;
    }

    public String getState(){
        return state;
    }

    public void setBook_name(String data){
        this.book_name = data;
    }
    public void setAuthor(String data){
        this.author = data;
    }
    public void setPublisher(String data){
        this.publisher = data;
    }
    public void setState(String data){
        this.state = data;
    }
}
