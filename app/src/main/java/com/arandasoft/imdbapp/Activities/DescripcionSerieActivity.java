package com.arandasoft.imdbapp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.arandasoft.imdbapp.Adapters.ItemEpisodiosAdapter;
import com.arandasoft.imdbapp.Items.ItemSeriesObject;
import com.arandasoft.imdbapp.R;
import com.arandasoft.imdbapp.conexion.AsyncTaskTestGET;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Vista para mirar los detalles de una Serie escojida por el usuario
 * @author: Klauss Sheffield Rendon Muñoz
 * @version: 1.0  25/04/2016.
 *
 */
public class DescripcionSerieActivity extends AppCompatActivity implements AsyncTaskTestGET.HttpI {

    //Campos de la Activity

    String id;
    String nombreSerie;
    String url;
    String apiKey;
    ProgressDialog circuloProgres;
    AsyncTaskTestGET asynTaskGet;
    ArrayList<ItemSeriesObject> rowListItem;
    String img_vacia;
    String enlace_img;
    ImageView imagePoster;
    TextView textViewNombreSerie;
    TextView textViewGeneros;
    TextView textViewActores;
    String backdrop_path;
    String numero_temporadas;
    ArrayList<String> generos;
    ArrayList<String> actores;
    ArrayList<View> botonesIdTextosTemporadas;
    ArrayList<String> episodios;
    ListView lst;
    ScrollView scrollViewEventDetails;
    Button volver;


    /*Metodo onCreate para crear la vista de la Actividad*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcion_activity);
        Bundle extras = getIntent().getExtras();

        /*Inicializamos algunos campos*/
        imagePoster = (ImageView) findViewById(R.id.imageViewImagenSerie);
        textViewNombreSerie = (TextView) findViewById(R.id.TextViewNombreSerie);
        textViewGeneros = (TextView) findViewById(R.id.TextViewGeneros);
        textViewActores = (TextView) findViewById(R.id.TextViewActores);

        img_vacia = this.getString(R.string.url_imagen_no_disponible);
        scrollViewEventDetails = (ScrollView) findViewById(R.id.ScrollView1);
        volver = (Button) findViewById(R.id.buttonVolver);

        generos = new ArrayList<String>();
        actores = new ArrayList<String>();
        episodios = new ArrayList<String>();
        botonesIdTextosTemporadas = new ArrayList<View>();
        if (extras != null) {
            id = extras.getString("id");
            nombreSerie = extras.getString("nombreSerie");
            rowListItem = extras.getParcelableArrayList("listaSeries");
        }

        /*Principalmente llamamos cargarDescripcion para poder obtener la descripcion de la serie
        * @param id de la Serie*/
        cargarDescripcion(id);

        /*Colocamos el titulo de la serie en el ActionBar*/
        getSupportActionBar().setTitle(nombreSerie);

        /*Boton de la flecha atras para poder volver a la vista de Busqueda*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Evento del Boton atras para poder volver a la vista de Busqueda*/
        volver.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                onBackPressed();
            }
        });
    }

/*El metodo cargarDescripcion mas series e dirige hacia la clase AsynTaskTestGet para conectarce con el cliente
    * le mandamos como parametro la actividad y la url con la pagina siguiente  para obtener respuesta de los datos de la serie
* @param id de la Serie*/
    public void cargarDescripcion(String id) {
        circuloProgres = new ProgressDialog(DescripcionSerieActivity.this);
        circuloProgres.setCancelable(false);
        circuloProgres.show();
        circuloProgres.setContentView(R.layout.custom_progressdialog);

        url = this.getResources().getString(R.string.descripcion_serie);
        apiKey = this.getResources().getString(R.string.apiKey);

        url = url + id + "?api_key=" + apiKey;

        asynTaskGet = new AsyncTaskTestGET(DescripcionSerieActivity.this, url);
        asynTaskGet.execute();


    }
    /*El metodo setResult es la implementacion de la clase AsynTaskTestGET, y obtenemos como respuesta un objeto Json*/
    @Override
    public void setResult(JSONObject json) throws JSONException {

        circuloProgres.cancel();


        String respJson = json.toString();


        if (json != null) {

            if (!respJson.equals("{}")) {

                if (json.opt("created_by") != null) {

                    tratarInformacionSeries(json);

                } else if (json.opt("cast") != null) {

                    tratarInformacionActores(json);
                } else {

                    tratarInformacionEpisodios(json);
                }


            } else {


                onBackPressed();

                Toast toast1 =
                        Toast.makeText(getApplicationContext(), "Lo sentimos intente de nuevo", Toast.LENGTH_SHORT);
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

    /*Metodo para tratar la Informacion de la serie recivimos un JSON*/
    public void tratarInformacionSeries(JSONObject json) throws JSONException {

        imagePoster = (ImageView) findViewById(R.id.imageViewImagenSerie);
        backdrop_path = json.getString("backdrop_path");
        if (backdrop_path.equals("null")) {
              /*Esta Libreria Llamada Piccasso Picasso permite cargar imagenes sin problemas
        en tu aplicacion para mas informacion  http://square.github.io/picasso/*/
            Picasso.with(this).load(img_vacia).into(imagePoster);

        } else {

            backdrop_path.replace("/", " ");
            enlace_img = this.getString(R.string.url_imagen);
            enlace_img = enlace_img + backdrop_path;
              /*Esta Libreria Llamada Piccasso Picasso permite cargar imagenes sin problemas
        en tu aplicacion para mas informacion  http://square.github.io/picasso/*/
            Picasso.with(DescripcionSerieActivity.this).load(enlace_img).into(imagePoster);
        }
        numero_temporadas = json.getString("number_of_seasons");


        String genconcatenados = "";

        JSONArray genresArray = json.getJSONArray("genres");
        if (genresArray.length() == 0) {
            genconcatenados = "No hay Información de generos";
        }

        for (int i = 0; i < genresArray.length(); i++) {
            generos.add(genresArray.getJSONObject(i).getString("name"));
        }

        if (generos.size() > 0) {
            int j = 0;
            for (String string : generos) {
                genconcatenados += string;
                j++;
                if (j == generos.size())
                    genconcatenados += ".";
                else
                    genconcatenados += ",";
            }

        }

        textViewNombreSerie.setText(nombreSerie);
        textViewGeneros.setText(genconcatenados);

/*Llamada al metodo cargarActores*/
        cargarActores();

    }


    /*El metodo cargarActores mas series e dirige hacia la clase AsynTaskTestGet para conectarce con el cliente
    * le mandamos como parametro la actividad y la url con la pagina siguiente  para obtener los actores de la serie
* @param id de la Serie*/
    public void cargarActores() {

        circuloProgres = new ProgressDialog(DescripcionSerieActivity.this);
        circuloProgres.setCancelable(false);
        circuloProgres.show();
        circuloProgres.setContentView(R.layout.custom_progressdialog);

        url = this.getResources().getString(R.string.descripcion_serie);
        apiKey = this.getResources().getString(R.string.apiKey);

        url = url + id + "/season/1/" + "credits?api_key=" + apiKey;

        asynTaskGet = new AsyncTaskTestGET(DescripcionSerieActivity.this, url);
        asynTaskGet.execute();


    }

    /*Metodo para tratar la Informacion de los actores de la serie recivimos un JSON*/
    public void tratarInformacionActores(JSONObject json) throws JSONException {
        String actconcatenados = "";

        JSONArray actoresArray = json.getJSONArray("cast");

        if (actoresArray.length() == 0) {
            actconcatenados = "No hay información de actores";
        }

        for (int i = 0; i < actoresArray.length(); i++) {
            actores.add(actoresArray.getJSONObject(i).getString("name"));
        }


        if (actores.size() > 0) {
            int j = 0;
            for (String string : actores) {
                actconcatenados += string;
                j++;
                if (j == actores.size())
                    actconcatenados += ".";
                else
                    actconcatenados += ",";
            }
        }

        textViewActores.setText(actconcatenados);


        /*Llamada al metodo cargarTemporadaButtons*/
        cargarTemporadasButtons();
        cargarEpisodios("1");


    }

    /*Metodo para crear los botones dinamicos con un scroll horizontal dependiendo del numero de temporadas de la serie*/
    public void cargarTemporadasButtons() {


        for (int i = 0; i < Integer.parseInt(numero_temporadas); i++) {
            final Button myButton = new Button(this);
            myButton.setText(String.valueOf(i + 1));
            myButton.setTag(i + 1);

            botonesIdTextosTemporadas.add(myButton);

            LinearLayout ll = (LinearLayout) findViewById(R.id.layoutButtons);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);


            myButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    episodios.clear();
                    /*Aqui llamamos el metodo cargarEpisodios y le madnamos como parametro el numero de temporada*/
                    cargarEpisodios(myButton.getTag().toString());

                }
            });


        }
    }

    /*El metodo cargarActores mas series e dirige hacia la clase AsynTaskTestGet para conectarce con el cliente
      * le mandamos como parametro la actividad y la url con la pagina siguiente  para obtener los episodios de la temporada
  * @param temporadaMotra de la Serie para poder visualizar los episodios*/
    public void cargarEpisodios(String temporadaMostrar) {

        circuloProgres = new ProgressDialog(DescripcionSerieActivity.this);
        circuloProgres.setCancelable(false);
        circuloProgres.show();
        circuloProgres.setContentView(R.layout.custom_progressdialog);

        url = this.getResources().getString(R.string.descripcion_serie);
        apiKey = this.getResources().getString(R.string.apiKey);

        url = url + id + "/season/" + temporadaMostrar + "?api_key=" + apiKey;

        asynTaskGet = new AsyncTaskTestGET(DescripcionSerieActivity.this, url);
        asynTaskGet.execute();


    }

    /*Metodo para tratar la Informacion de los episodios de la temporada recivimos un JSON*/
    public void tratarInformacionEpisodios(JSONObject json) throws JSONException {

        JSONArray episodiosArray = json.getJSONArray("episodes");

        for (int i = 0; i < episodiosArray.length(); i++) {
            episodios.add(episodiosArray.getJSONObject(i).getString("name"));

       }

        int tamanioListView = 55;

        tamanioListView = tamanioListView * episodios.size();


        lst = (ListView) findViewById(R.id.listViewEpisodios);
        /*Dependiendo de cuantos episodios hay en la temporada alargamos el ListView para poder visulaizarlos*/
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) lst.getLayoutParams();
        lp.height = tamanioListView;
        lst.setLayoutParams(lp);

        /*Adaptador de los items de la lista para cada uno de los Episodios*/
        ItemEpisodiosAdapter itemEpisodiosAdapter;
        itemEpisodiosAdapter = new ItemEpisodiosAdapter(this, episodios);
        lst.setAdapter(itemEpisodiosAdapter);

        scrollViewEventDetails.fullScroll(View.FOCUS_UP);

    }

    /*Metodo para icono flecha para volver atras*/
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;

    }

    /*Metodo que utliza el boton atras y el icono flecha para volver a la activity anterior*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
