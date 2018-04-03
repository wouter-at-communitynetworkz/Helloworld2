package com.roundabout.logtologstash;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args)
            throws Exception
    {
        HttpInteractor httpInteractor = new HttpInteractor();
        System.out.println(httpInteractor.logToLogstash("hola"));
        System.out.println(httpInteractor.logToLogstash(2345));
        String[] myStringArray;
        myStringArray = new String[]{"a","b","c"};
        System.out.println(httpInteractor.logToLogstash(myStringArray));
        // GSON
        Gson gson = new Gson();

        for (String s : args) {
            System.out.println("Args: " + s);
        }

        //System.out.println("Done : geson = " + gson.toString());
        System.out.println("log to logstash");
    }
}