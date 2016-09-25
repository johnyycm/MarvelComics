package com.example.chenmin.marvelcomics;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by chenmin on 2016-09-23.
 */

public class ImageLoaderSingleton {
    private static ImageLoaderSingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public static ImageLoaderSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ImageLoaderSingleton(context);
        }
        return mInstance;
    }

    private ImageLoaderSingleton(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        ImageLoader.ImageCache mImageCache = new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(100);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        };
        mImageLoader = new ImageLoader(this.mRequestQueue, mImageCache);
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }

}
