package com.example.moviebrowser;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity  implements  MovieFragment.OnListFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void movieSelector(Movie item) {

    int display_mode = getResources().getConfiguration().orientation;
    if(display_mode == Configuration.ORIENTATION_PORTRAIT){
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("movie", item);
        startActivity(intent);
    }else{
        DetailsFragment df = (DetailsFragment) getSupportFragmentManager().findFragmentByTag("details");
        if( df == null){
            FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
            df = DetailsFragment.newInstance(item);
            fts.add(R.id.container, df, "details");
            fts.commit();
            }else {
            df.setMovie(item, findViewById(R.id.container));

            }
        }
    }
}
