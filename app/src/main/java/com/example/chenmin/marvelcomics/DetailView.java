package com.example.chenmin.marvelcomics;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by chenmin on 2016-09-23.
 */

public class DetailView extends DialogFragment {
    private ComicBook mComicBook;
    private TextView title;
    private ImageView imageView;
    private TextView description;
    private Button mButton;
    private TextView id, modifiedDate;
    private Bitmap bitmap;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setComicBook(ComicBook comicBook) {
        mComicBook = comicBook;
    }

    public static DetailView newInstance() {
        return new DetailView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_view, container);
        findViews(view);
        setUpViews();
        return view;
    }

    private void findViews(View view) {
        title = (TextView)view.findViewById(R.id.tv_title);
        id = (TextView) view.findViewById(R.id.tv_id);
        modifiedDate = (TextView) view.findViewById(R.id.tv_modified_date);
        imageView = (ImageView) view.findViewById(R.id.iv_detail);
        description = (TextView) view.findViewById(R.id.tv_description);
        mButton = (Button) view.findViewById(R.id.btn_dismiss);
    }

    private void setUpViews() {
        title.setText(mComicBook.getTitle());
        id.setText("Id: " + String.valueOf(mComicBook.getId()));
        modifiedDate.setText("Date: " + mComicBook.getDate());
        imageView.setImageBitmap(bitmap);
        if (mComicBook.getDescription().length() > 0) {
            description.setText(mComicBook.getDescription());
            if (mComicBook.getDescription().equals("null")){
                description.setText("Description not available");
            }
        } else {
            description.setVisibility(View.GONE);
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailView.this.dismiss();
            }
        });
    }

}
