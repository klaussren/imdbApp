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



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders>  {

    private ArrayList<ItemSeriesObject> itemList;
    private Context context;
    RecyclerViewHolders recHolder;

    public RecyclerViewAdapter(Context context, ArrayList<ItemSeriesObject> itemList) {
        this.itemList = itemList;
        this.context = context;

    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView,itemList);


        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        holder.nombreSerie.setText(itemList.get(position).getName());

        String enlace=context.getResources().getString(R.string.url_imagen);
        String image_url = enlace + itemList.get(position).getPhoto();






        /*Esta Libreria Llamada Piccasso Picasso permite cargar imagenes sin problemas
        en tu aplicacion para mas informacion  http://square.github.io/picasso/*/


        Picasso.with(context).load(image_url).into(holder.imagenSerie);



    }





    @Override
    public int getItemCount() {

        return this.itemList.size();
    }
}
