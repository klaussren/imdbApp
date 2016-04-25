package com.arandasoft.imdbapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arandasoft.imdbapp.Items.ItemSeriesObject;
import com.arandasoft.imdbapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Clase Adaptador para los items de cada una de las series encontradas
 * @author: Klauss Sheffield Rendon Muñoz
 * @version: 1.0  25/04/2016.
 *
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders>  {

    //Campos de la clase
    private ArrayList<ItemSeriesObject> itemList;
    private Context context;
    RecyclerViewHolders recHolder;

    public RecyclerViewAdapter(Context context, ArrayList<ItemSeriesObject> itemList) {
        this.itemList = itemList;
        this.context = context;

    }
    /* Creador de la viewHolder para poder visualizar cada uno de los items de la serie*/
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView,itemList,context);


        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        /*Obtenemos el nombre de la serie para fijarlo en el TextView*/
        holder.nombreSerie.setText(itemList.get(position).getName());

        /*Obtenemos la url del arrayList dependiendo de la posicion*/
        String image_url =  itemList.get(position).getPhoto();



        /*Esta Libreria Llamada Piccasso Picasso permite cargar imagenes sin problemas
        en tu aplicacion para mas informacion  http://square.github.io/picasso/*/

        Picasso.with(context).load(image_url).into(holder.imagenSerie);



    }





    @Override
    public int getItemCount() {

        return this.itemList.size();
    }
}
