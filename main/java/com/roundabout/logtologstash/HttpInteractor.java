package com.roundabout.logtologstash;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;


/**
 *
 * A complete Java class that shows how to open a URL, then read data (text) from that URL,
 * HttpURLConnection class (in combination with an InputStreamReader and BufferedReader).
 *
 * Adapted for personal use by wouter
 *
 *
 * @author alvin alexander, http://alvinalexander.com.
 *
 */
public class HttpInteractor {

    private String protocol;
    private String domain;
    private String path;
    private String method;
    private String myUrl;
    //constructor
    public HttpInteractor()
    {
        try
        {
            protocol = "https";
            domain = "napnop.net";
            path = "";
            path = URLEncoder.encode(path, "UTF-8");
            method = "PUT";

            myUrl = protocol + "://" + domain + "/" + path;
            // if your url can contain weird characters you will want to
            // encode it here, something like this:
            //myUrl = URLEncoder.encode(myUrl, "UTF-8");

            String results = doHttpUrlConnectionAction(myUrl, method);
            System.out.println(results);
        }
        catch (Exception e)
        {
            // deal with the exception in your "controller"
        }
    }


    /**
     * Returns the output from the given URL.
     *
     * I tried to hide some of the ugliness of the exception-handling
     * in this method, and just return a high level Exception from here.
     * Modify this behavior as desired.
     *
     * @param desiredUrl
     * @return
     * @throws Exception
     */
    private String doHttpUrlConnectionAction(String desiredUrl, String method)
            throws Exception
    {

        StackTraceElement[] plof = Thread.currentThread().getStackTrace();
        System.out.println(plof[plof.length-2]);
        int callersLineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        System.out.println(callersLineNumber);
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        System.out.println(stackTraceElements[1].getFileName());
        System.out.println(stackTraceElements[1].getLineNumber());
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try
        {
            // create the HttpURLConnection
            url = new URL(desiredUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP GET here
//            connection.setRequestMethod("GET");
//            connection.setRequestMethod("PUT");
            // connection.setRequestMethod(method);



            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

            map.put("user", "system");
            map.put("caller", "this_file");
            map.put("caller-line", "this_line");
            map.put("node-nam", "this_nod");
            map.put("lgsthenv","lgsth");
            map.put("message","logitem");

            Gson gson = new Gson();
            String json = gson.toJson(map,LinkedHashMap.class);
            //new JSONObject(map);
            String urlParameters  = "json=" + json;
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;

            // uncomment this if you want to write output to this url
            connection.setDoOutput(true);

            connection.setDoOutput( true );
            connection.setInstanceFollowRedirects( false );
            connection.setRequestMethod( "POST" );

            connection.setRequestProperty( "Content-Type", "application/json");

            connection.setRequestProperty( "Accept", "application/json");
            connection.setRequestProperty( "charset", "utf-8");

            connection.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( connection.getOutputStream())) {
                wr.write( postData );
            }




            // give it 15 seconds to respond
            connection.setReadTimeout(15*1000);
            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public String logToLogstash(String message){

        StackTraceElement[] plof = Thread.currentThread().getStackTrace();
        System.out.println(plof[plof.length-1]);

        return "loggen man string";
    }
    public String logToLogstash(Object message){

        StackTraceElement[] plof = Thread.currentThread().getStackTrace();
        System.out.println(plof[plof.length-1]);

        return "loggen man object";
    }
    public String logToLogstash(int message){

        StackTraceElement[] plof = Thread.currentThread().getStackTrace();
        System.out.println(plof[plof.length-1]);

        return "loggen man int";
    }

}

