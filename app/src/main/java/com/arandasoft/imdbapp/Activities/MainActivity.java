package com.arandasoft.imdbapp.Activities;
/**
 * Created by KLAUSSREN on 23/04/2016.
 */

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.arandasoft.imdbapp.Adapters.RecyclerViewAdapter;
import com.arandasoft.imdbapp.Items.ItemSeriesObject;
import com.arandasoft.imdbapp.R;
import com.arandasoft.imdbapp.conexion.AsyncTaskTestGET;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncTaskTestGET.HttpI {

    private GridLayoutManager lLayout;
    AsyncTaskTestGET asynTaskGet;
    ProgressDialog circuloProgres;

    RecyclerView rView;


    private boolean loading = true;


    int pastVisiblesItems, visibleItemCount, totalItemCount;


    ArrayList<ItemSeriesObject> rowListItem;


    private int ival = 1;
    private int loadLimit = 10;

    RecyclerViewAdapter rcAdapter;
    String query = "";
    EditText editTextBuscar;
    String url;
    String apiKey;
    ImageView buttonBuscar;

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

                    url = MainActivity.this.getResources().getString(R.string.url_buscar_serie);
                    apiKey = MainActivity.this.getResources().getString(R.string.apiKey);

                    url=url+apiKey+"&"+"query="+query;

                    asynTaskGet = new AsyncTaskTestGET(MainActivity.this, url, 1);
                    asynTaskGet.execute();



            }
            }
        });

        rowListItem = getAllItemList();


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

                            if (totalItemCount >= 40) {
                                loading = false;
                            } else {
                                loadMoreData();
                            }

                        }
                    }


                }
            }
        });

        rcAdapter = new RecyclerViewAdapter(MainActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);








    }

    private void loadMoreData() {

        circuloProgres = new ProgressDialog(this);
        circuloProgres.setCancelable(false);
        circuloProgres.show();
        circuloProgres.setContentView(R.layout.custom_progressdialog);
        loadLimit = ival + 9;
        for (int i = ival; i <= loadLimit; i++) {
            rowListItem.add(new ItemSeriesObject(String.valueOf(ival), "mUkuc2wyV9dHLG0D0Loaw5pO2s8.jpg"));
            ival++;
        }
        if (ival >= loadLimit) {
            circuloProgres.cancel();
        }
        rcAdapter.notifyDataSetChanged();
        loading = true;
    }

    private ArrayList<ItemSeriesObject> getAllItemList() {


        ArrayList<ItemSeriesObject> allItems = new ArrayList<ItemSeriesObject>();

        for (int i = ival; i <= loadLimit; i++) {
            allItems.add(new ItemSeriesObject(String.valueOf(ival), "mUkuc2wyV9dHLG0D0Loaw5pO2s8.jpg"));
            ival++;

        }
        return allItems;
    }

    @Override
    public void setResult(JSONObject json) throws JSONException {

        circuloProgres.cancel();

        if (json != null) {

        }


    }


}
