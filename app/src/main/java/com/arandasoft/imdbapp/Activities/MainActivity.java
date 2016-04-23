package com.arandasoft.imdbapp.Activities;
/**
 * Created by KLAUSSREN on 23/04/2016.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arandasoft.imdbapp.Adapters.RecyclerViewAdapter;
import com.arandasoft.imdbapp.Items.ItemObject;
import com.arandasoft.imdbapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ItemObject> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(MainActivity.this,2);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);

    }

    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("Uno", "mUkuc2wyV9dHLG0D0Loaw5pO2s8.jpg"));
        allItems.add(new ItemObject("Dos", "lG2dtq0bRnNMi4icQ3slAMVhb3c.jpg"));
        allItems.add(new ItemObject("Tres", "/1c74R5pUh8rESqqQIs0bgetj8sZ.jpg"));
        allItems.add(new ItemObject("Cuatro", "/1BctTvvzA0afApgZMmtXnfvy5G4.jpg"));
        allItems.add(new ItemObject("Cinco","/qS6O6guamoyzQNISYTWVd1PkCEJ.jpg"));
        return allItems;
    }
}
