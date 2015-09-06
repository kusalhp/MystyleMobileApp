package com.example.dell.test2;

/**
 * Created by Dell on 9/5/2015.
 */
public class CommentList {

    private String name,comment,date;

    public String getName() {
        return name;
    }

    public CommentList(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public CommentList(String name, String comment, String date) {
        this.name = name;
        this.comment = comment;
        this.date = date;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
