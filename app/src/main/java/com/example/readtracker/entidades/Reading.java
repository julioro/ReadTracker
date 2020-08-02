package com.example.readtracker.entidades;

import java.io.Serializable;
import java.util.Date;

public class Reading implements Serializable {

    private String id;
    private String title;
    private boolean status;
    private String url;
    private int pages;
    private String label;
    private Date readDate;
    //private User user;
    private String userId;
    private String author;

    public Reading(){

    }

    public Reading(String valTitle, String valAuthor, int valPages, String valUrl, String valLabel){

        this.title = valTitle;
        this.author = valAuthor;
        this.pages = valPages;
        this.url = valUrl;
        this.label = valLabel;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPages() {
        return pages;
    }

    public void setPagess(int pages) {
        this.pages = pages;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

   /* public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    */

}
