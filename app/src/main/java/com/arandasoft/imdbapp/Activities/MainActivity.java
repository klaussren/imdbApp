package com.arandasoft.imdbapp.Activities;
/**
 * Created by KLAUSSREN on 23/04/2016.
 */

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.arandasoft.imdbapp.Adapters.RecyclerViewAdapter;
import com.arandasoft.imdbapp.Items.ItemSeriesObject;
import com.arandasoft.imdbapp.R;
import com.arandasoft.imdbapp.conexion.AsyncTaskTestGET;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncTaskTestGET.HttpI {

    private GridLayoutManager lLayout;
    AsyncTaskTestGET asynTaskGet, asynTaskGet2;
    ProgressDialog circuloProgres;

    RecyclerView rView;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    ArrayList<ItemSeriesObject> rowListItem;

    RecyclerViewAdapter rcAdapter;
    String query = "";
    EditText editTextBuscar;
    String url;
    String apiKey;
    ImageView buttonBuscar;

    String pagina = "";
    String total_results = "";
    String total_pages = "";
    int pag;
    boolean banderaConsulta = true;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextBuscar = (EditText) findViewById(R.id.editTextBuscar);


        buttonBuscar = (ImageView) findViewById(R.id.buttonBuscar);
        buttonBuscar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                query = editTextBuscar.getText().toString();
                if (!query.equals("")) {

                    circuloProgres = new ProgressDialog(MainActivity.this);
                    circuloProgres.setCancelable(false);
                    circuloProgres.show();
                    circuloProgres.setContentView(R.layout.custom_progressdialog);
                    loading = true;
                    rowListItem = new ArrayList<ItemSeriesObject>();
                    query = query.toLowerCase();
                    query = query.replace(" ", "+");


                    url = MainActivity.this.getResources().getString(R.string.url_buscar_serie);
                    apiKey = MainActivity.this.getResources().getString(R.string.apiKey);

                    url = url + apiKey + "&" + "query=" + query;

                    asynTaskGet = new AsyncTaskTestGET(MainActivity.this, url);
                    asynTaskGet.execute();


                }
            }
        });


        lLayout = new GridLayoutManager(MainActivity.this, 2);
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        rView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {

                    visibleItemCount = rView.getChildCount();
                    totalItemCount = lLayout.getItemCount();
                    pastVisiblesItems = lLayout.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                            Toast toast1 =
                                    Toast.makeText(getApplicationContext(), "Legue al final de los items " + String.valueOf(totalItemCount), Toast.LENGTH_SHORT);
                            toast1.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);

                            toast1.show();


                            loading = false;

                            banderaConsulta = true;
                            cargarMasDSeries();


                        }
                    }


                }
            }
        });


    }

    private void cargarMasDSeries() {


        pag = Integer.parseInt(pagina) + 1;

        String p = String.valueOf(pag);

        url = url + "&page=" + p;

        if (banderaConsulta == true) {
            banderaConsulta = false;
            asynTaskGet2 = new AsyncTaskTestGET(MainActivity.this, url);
            asynTaskGet2.execute();

        }

    }



    @Override
    public void setResult(JSONObject json) throws JSONException {


        circuloProgres.cancel();


        Window window = this.getWindow();

        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);


        String respJson = json.toString();


        if (json != null) {

            if (!respJson.equals("{}")) {

                tratarInformacionSeries(json);


            } else {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(), "Lo sentimos intente de nuevo  ", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);

                toast1.show();


            }

        } else {

            Toast toast1 =
                    Toast.makeText(getApplicationContext(), "Lo sentimos intente de nuevo  ", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);

            toast1.show();

        }


    }

    public void tratarInformacionSeries(JSONObject json) throws JSONException {

        circuloProgres.cancel();
        pagina = json.getString("page");
        total_results = json.getString("total_results");
        total_pages = json.getString("total_pages");


        String poster_path = "";
        String id = "";
        String name = "";


        JSONArray resultsArray = json.getJSONArray("results");

        for (int i = 0; i < resultsArray.length(); i++) {


            id = resultsArray.getJSONObject(i).getString("id");
            name = resultsArray.getJSONObject(i).getString("name");
            poster_path = resultsArray.getJSONObject(i).getString("poster_path");

            rowListItem.add(new ItemSeriesObject(id, name, poster_path));

        }

        if (rowListItem.size() <= 20) {

            rcAdapter = new RecyclerViewAdapter(MainActivity.this, rowListItem);
            rView.setAdapter(rcAdapter);
        } else {
            loading = true;
            banderaConsulta = true;
            rcAdapter.notifyDataSetChanged();
        }
    }


    /*Con estos dos metodos guardamos y recuperamos el estado de nuestro ArrayList de series cuando se cambia la
    * pantalla de modo landscape a portrait*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putSerializable("listaSeries", rowListItem);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            rowListItem = (ArrayList<ItemSeriesObject>) savedInstanceState.getSerializable("listaSeries");
            rcAdapter = new RecyclerViewAdapter(MainActivity.this, rowListItem);
            rView.setAdapter(rcAdapter);
        }
    }


}
