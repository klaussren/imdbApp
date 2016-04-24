package com.arandasoft.imdbapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arandasoft.imdbapp.Items.ItemSeriesObject;
import com.arandasoft.imdbapp.R;

import java.util.ArrayList;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{


    public ImageView imagenSerie;
    public TextView nombreSerie;
    public ArrayList<ItemSeriesObject> itemList;

    public RecyclerViewHolders(View itemView,ArrayList<ItemSeriesObject> itemList) {
        super(itemView);
        itemView.setOnClickListener(this);
        nombreSerie = (TextView)itemView.findViewById(R.id.textViewNombreSerie);
        imagenSerie = (ImageView)itemView.findViewById(R.id.imageViewImagenSerie);
        this.itemList=itemList;
    }


    @Override
    public void onClick(View view) {
       // Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Toast.makeText(view.getContext(), "Clicked Country Position = " + itemList.get(getPosition()).getName(), Toast.LENGTH_SHORT).show();
    }
}