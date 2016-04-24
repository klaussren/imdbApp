package com.arandasoft.imdbapp.conexion;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by KLAUSSREN on 23/04/2016.
 */
public class AsyncTaskTestGET extends AsyncTask<String, Void, JSONObject> {

    JSONObject json = new JSONObject();
    HttpI httpI;
    private String url;

    public AsyncTaskTestGET(HttpI httpI, String url) {
        this.httpI = httpI;
        this.url=url;
    }

    public interface HttpI {
        public void setResult(JSONObject json) throws JSONException;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        json = obtenerRespuesta ();
        return null;
    }


    public JSONObject obtenerRespuesta () {

        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpget = new HttpGet("http://api.themoviedb.org/3/search/tv?api_key=11cdf899e4e3f06b0af58b5c936448fb&query=g");
        //set header to tell REST endpoint the request and response content types
        //httpget.setHeader("Accept", "application/json");
        httpget.setHeader("Content-type", "application/json");
    //    httpget.setHeader("Authorization", "ApiKey 11cdf899e4e3f06b0af58b5c936448fb");
   //     httpget.setHeader("Authorization", "11cdf899e4e3f06b0af58b5c936448fb");


    /*    URL url = new URL("http://api.themoviedb.org/3/search/tv?api_key=11cdf899e4e3f06b0af58b5c936448fb&query=g");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");

// read the response
        System.out.println("Response Code: " + conn.getResponseCode());
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
        System.out.println(response);*/

        try {

            HttpResponse response = httpclient.execute(httpget);

            String res = response.getStatusLine().toString();
            String jsonResponse = "";
            if (res.contains("401")||res.contains("403") ) {
                jsonResponse = "[\n" + "  \"codigo error\":\"permisos\"\n" + "]";
            }
            else if (!res.contains("200"))
                jsonResponse = "[\n" + "  \"codigo error\":\"desconcido\"\n" + "]";

            //read the response and convert it into JSON array
            json = new JSONObject(EntityUtils.toString(response.getEntity()));


            //return the JSON array for post processing to onPostExecute function
            return json;

        }catch (Exception e) {
            Log.v("Error adding article", e.getMessage());
        }



        return json;
    }


    protected void onPostExecute(JSONObject result) {

        try {
            httpI.setResult(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
