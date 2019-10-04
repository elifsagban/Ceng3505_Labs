package com.example.listviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArrayAdapterActivity extends ListActivity {

    static String[] ANIMALS = new String[] {"Ant", "Bear", "Monkey", "Elephant", "Bird", "Cat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_array_adapter);

        ListView ListView = getListView();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_array_adapter,ANIMALS);
        setListAdapter(adapter);
        ListView.setTextFilterEnabled(true);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ArrayAdapterActivity.this,((TextView)view).getText(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
