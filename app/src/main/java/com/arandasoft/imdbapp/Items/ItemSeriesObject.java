package com.arandasoft.imdbapp.Items;


import android.os.Parcel;
import android.os.Parcelable;


/**
 * Clase para mapear los JsonObject principales de la busqueda.
 * @author: Klauss Sheffield Rendon Muñoz
 * @version: 1.0  25/04/2016.
 *
 */

public class ItemSeriesObject implements Parcelable {

    //Campos de la clase

    private String id;
    private String name;
    private String photo;

/* Se implementa el metodo Parcerlable para poder pasar parametros entre activities*/
    public static final Parcelable.Creator<ItemSeriesObject> CREATOR = new Parcelable.Creator<ItemSeriesObject>() {
        public ItemSeriesObject createFromParcel(Parcel in) {

            return new ItemSeriesObject(in);
        }
        public ItemSeriesObject[] newArray(int size) {
            return new ItemSeriesObject[size];
        }
    };

    /*Metodo para hacer parcelable la clase*/

    public ItemSeriesObject(Parcel in){
        readFromParcel(in);
    }
    /*Metodo para hacer parcelable la clase*/
    private void readFromParcel(Parcel in) {

        id=in.readString();
        name = in.readString();
        photo = in.readString();

    }
    /*Metodo para hacer parcelable la clase*/
    @Override
    public int describeContents() {
        return 0;
    }
    /*Metodo para hacer parcelable la clase*/
    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        // TODO Auto-generated method stub

        arg0.writeString(id);

        arg0.writeString(name);
        arg0.writeString(photo);
    }

    /*Constructor por defecto parametrizado*/
    public ItemSeriesObject(String id,String name, String photo) {
        this.id=id;
        this.name = name;
        this.photo = photo;
    }

    /*Geters y Seters de cada uno de las variables de la clase*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
