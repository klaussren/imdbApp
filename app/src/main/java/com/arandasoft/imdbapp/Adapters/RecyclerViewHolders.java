package com.arandasoft.imdbapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arandasoft.imdbapp.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{


    public ImageView imagenSerie;
    public TextView nombreSerie;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        nombreSerie = (TextView)itemView.findViewById(R.id.textViewNombreSerie);
        imagenSerie = (ImageView)itemView.findViewById(R.id.imageViewImagenSerie);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}