package com.example.chenmin.marvelcomics;

import java.util.ArrayList;

/**
 * Created by chenmin on 2016-09-23.
 */

public interface ListingListener {
    void downloadFinished(ArrayList<ComicBook> list);
}
