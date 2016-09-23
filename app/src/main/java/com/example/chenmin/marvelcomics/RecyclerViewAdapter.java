package com.example.chenmin.marvelcomics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenmin on 2016-09-23.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<ComicBook> mComicBooks;
    private ImageLoader imageLoader;
    private MainActivity mMainActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public NetworkImageView mNetworkImageView;
        public ViewHolder(View v) {
            super(v);
            mNetworkImageView = (NetworkImageView) v.findViewById(R.id.iv_grid);
        }
    }

    public RecyclerViewAdapter(ArrayList<ComicBook> list, MainActivity activity){
        mComicBooks = list;
        imageLoader = ImageLoaderSingleton.getInstance(activity.getApplicationContext()).getImageLoader();
        mMainActivity = activity;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mNetworkImageView.setImageUrl(mComicBooks.get(position).getThumbnailPath(),imageLoader);
        holder.mNetworkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailView detailView = DetailView.newInstance();
                detailView.setComicBook(mComicBooks.get(position));
                detailView.show(mMainActivity.getFragmentManager(),"detailView");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mComicBooks.size() ;
    }
}
