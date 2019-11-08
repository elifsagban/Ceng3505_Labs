package com.example.moviebrowser;

import android.content.Intent;
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
    Intent intent = new Intent(this, DetailsActivity.class);
    intent.putExtra("movie", item);
    startActivity(intent);

    }
}
