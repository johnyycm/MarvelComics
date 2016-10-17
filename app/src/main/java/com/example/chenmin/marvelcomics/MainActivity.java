package com.example.chenmin.marvelcomics;

import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListingListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MarvelComicsClient mMarvelComicsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.myToolbar) ;
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a grid layout manager
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //download comics
        mMarvelComicsClient = new MarvelComicsClient(getApplicationContext(), this);
        mMarvelComicsClient.getComics();
    }


    @Override
    public void downloadFinished(ArrayList<ComicBook> list) {
        // specify an adapter
        mAdapter = new RecyclerViewAdapter(list, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int displayWidth = size.x;
        int column = displayWidth / convertDpToPx(180);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, column));
    }

    private int convertDpToPx(int dp) {
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }
}
