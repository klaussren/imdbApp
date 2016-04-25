package com.arandasoft.imdbapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arandasoft.imdbapp.R;

import java.util.ArrayList;

/**
 * Clase Item para poder visualizar cada uno de los episodios de la serie si los tiene
 * @author: Klauss Sheffield Rendon Muñoz
 * @version: 1.0  25/04/2016.
 *
 */
public class ItemEpisodiosAdapter extends BaseAdapter {

    //Campos de la clase
    Context context;
    protected ArrayList<String> episodios;
    String nombreEpisodio;
    TextView textViewEpisodio;
    TextView  textViewNumEpisodio;

    public ItemEpisodiosAdapter(Context context, ArrayList<String> episodios){
        super();
        this.context=context;
        this.episodios=episodios;
    }


    @Override
    public int getCount() {
        return episodios.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*Metodo para la vist de los Episodios de la temporada*/
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.item_episodios, null);
        View vi = view;

        nombreEpisodio = episodios.get(position);

        textViewNumEpisodio = (TextView) vi.findViewById(R.id.textViewNumEpisodio);
        textViewEpisodio = (TextView) vi.findViewById(R.id.textViewNombreEpisodio);

        /*Fijamos el numero de episodio y el nombre que obtenemos del arrayList de strings*/
        textViewNumEpisodio.setText(String.valueOf(position+1));
        textViewEpisodio.setText(nombreEpisodio);



        return vi;
    }
}
