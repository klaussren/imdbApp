package com.arandasoft.imdbapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arandasoft.imdbapp.Activities.DescripcionSerieActivity;
import com.arandasoft.imdbapp.Items.ItemSeriesObject;
import com.arandasoft.imdbapp.R;

import java.util.ArrayList;


/**
 * Clase RecyclerViewHolders que implementa el click de cada uno de los items y minimiza el
 * número de llamadas al costoso método findViewById.
 * @author: Klauss Sheffield Rendon Muñoz
 * @version: 1.0  25/04/2016.
 *
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{


    public ImageView imagenSerie;
    public TextView nombreSerie;
    public ArrayList<ItemSeriesObject> itemList;
    String idSerie;
    public Context context;


    /*Construrtor por defecto parametrizado del RecyclerViewHolders*/
    public RecyclerViewHolders(View itemView,ArrayList<ItemSeriesObject> itemList,Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        nombreSerie = (TextView)itemView.findViewById(R.id.textViewNombreSerie);
        imagenSerie = (ImageView)itemView.findViewById(R.id.imageViewImagenSerie);
        this.itemList=itemList;
        this.context=context;
    }

    /*Evento del click para la serie escogida el cual le mandamos extras "datos" para la actividad DescripcionSerieActivity*/
    @Override
    public void onClick(View view) {

        idSerie=itemList.get(getPosition()).getId();

        Intent myIntent= new Intent();
        myIntent.putExtra("id", idSerie);
        myIntent.putExtra("nombreSerie",itemList.get(getPosition()).getName());

        myIntent.putParcelableArrayListExtra("listaSeries",(ArrayList<? extends Parcelable>) itemList);
        myIntent.setClass(context.getApplicationContext(), DescripcionSerieActivity.class);
        context.startActivity(myIntent);
    }
}