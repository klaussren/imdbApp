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


/**
 * Created by KLAUSSREN on 23/04/2016.
 */
public class AsyncTaskTestGET extends AsyncTask<String, Void, JSONObject> {

    JSONObject json = new JSONObject();
    HttpI httpI;

    private String url;
    int consulta;

    public AsyncTaskTestGET(HttpI httpI, String url,int consulta) {
        this.httpI = httpI;
        this.url=url;
        this.consulta=consulta;
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




        HttpGet httpget = new HttpGet(url);
        //set header to tell REST endpoint the request and response content types
        //httpget.setHeader("Accept", "application/json");
        httpget.setHeader("Content-type", "application/json");

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
