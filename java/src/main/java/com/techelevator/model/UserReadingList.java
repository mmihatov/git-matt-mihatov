package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class UserReadingList {

    private int userID;

    private int listID;

    private String username;


    private List<Book> readingList = new ArrayList<>();

    public UserReadingList() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Book> getReadingList() {
        return readingList;
    }

    public void setReadingList(List<Book> readingList) {
        this.readingList = readingList;
    }
}
