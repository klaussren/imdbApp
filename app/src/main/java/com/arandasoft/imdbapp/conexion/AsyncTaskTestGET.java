package com.arandasoft.imdbapp.conexion;

/**
 * Clase para hacer la peticion Request para consumir los servicios
 * @author: Klauss Sheffield Rendon Muñoz
 * @version: 1.0  25/04/2016.
 *
 */

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



public class AsyncTaskTestGET extends AsyncTask<String, Void, JSONObject> {

    //Campos de la clase
    JSONObject json = new JSONObject();
    HttpI httpI;
    private String url;

    /* Constructor por defecto parametrizado */
    public AsyncTaskTestGET(HttpI httpI, String url) {
        this.httpI = httpI;
        this.url=url;
    }

    /*Declaracion de la Interfaz HttpI*/
    public interface HttpI {
        public void setResult(JSONObject json) throws JSONException;
    }

    /*Metodo en el cual se llama el metodo obtenerRespuesta y obtener el objeto JSON*/
    @Override
    protected JSONObject doInBackground(String... strings) {
        json = obtenerRespuesta ();
        return null;
    }

    /*Metodo para consumir el servicio y obtener el objeto JSON*/
    public JSONObject obtenerRespuesta () {

        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpget = new HttpGet(url);
        //set header to tell REST endpoint the request and response content types

        httpget.setHeader("Content-type", "application/json");

        httpget.setHeader("Accept", "application/json");
        httpget.setHeader("ETag", "d3a69fdeee228ff6582e6d1d7c7073ee");





        try {
            /*Aqui obtenemos la respuesta del servicio*/

            HttpResponse response = httpclient.execute(httpget);

            /*Obtenemos el objeto JSON del */
            json = new JSONObject(EntityUtils.toString(response.getEntity()));




            //return the JSON array for post processing to onPostExecute function
            return json;

        }catch (Exception e) {
            /*Lo que se hace qui es volver a intentar consumir el servicio para obtener la respuesta*/
            String respJson = json.toString();
            if (respJson.equals("{}")) {
                obtenerRespuesta();
            }
            Log.v("Error adding article", e.getMessage());
        }



        return json;
    }

/* Metodo en el cual se fija el objeto JSON y retornar el resultado a la activity
* @param  result*/
    protected void onPostExecute(JSONObject result) {

        try {
            httpI.setResult(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
