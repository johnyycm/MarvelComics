package com.example.chenmin.marvelcomics;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by chenmin on 2016-09-23.
 */

public class DetailView extends DialogFragment {
    private ComicBook mComicBook;
    private NetworkImageView mNetworkImageView;
    private ImageLoader mImageLoader;
    private TextView description;
    private Button mButton;
    private TextView id, modifiedDate;

    public void setComicBook(ComicBook comicBook) {
        mComicBook = comicBook;
    }

    public static DetailView newInstance() {
        DetailView detailView = new DetailView();
        return detailView;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_view,container);
        getDialog().setTitle(mComicBook.getTitle());
        id = (TextView)view.findViewById(R.id.tv_id);
        id.setText("Id: "+String.valueOf(mComicBook.getId()));
        modifiedDate =(TextView)view.findViewById(R.id.tv_modified_date);
        modifiedDate.setText("Date: "+mComicBook.getDate());
        mNetworkImageView = (NetworkImageView) view.findViewById(R.id.iv_detail);
        mImageLoader = ImageLoaderSingleton.getInstance(getActivity().getApplicationContext()).getImageLoader();
        mNetworkImageView.setImageUrl(mComicBook.getThumbnailPath(),mImageLoader);
        description = (TextView)view.findViewById(R.id.tv_description);
        if (mComicBook.getDescription().length()>0){
            description.setText(mComicBook.getDescription());
        }else{
            description.setVisibility(View.GONE);
        }
        mButton = (Button)view.findViewById(R.id.btn_dismiss);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailView.this.dismiss();
            }
        });
        return view;
    }



}
