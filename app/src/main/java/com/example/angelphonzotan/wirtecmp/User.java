package com.example.angelphonzotan.wirtecmp;

/**
 * Created by Anjoh on 12/03/2018.
 */

public class User {
    private String name;
    private String time;
    private int id;
    public User(){

    }

    public User(String n, String t){
        name = n;
        time = t;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
