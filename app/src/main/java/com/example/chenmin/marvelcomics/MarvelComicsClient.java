package com.example.chenmin.marvelcomics;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by chenmin on 2016-09-23.
 */

public class MarvelComicsClient {
    private static RequestQueue queue;
    private ArrayList<ComicBook> comicBooks;
    private ListingListener mListingListener;

    public MarvelComicsClient(Context context, ListingListener listener) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        mListingListener = listener;
    }

    private String url = "http://gateway.marvel.com/v1/public/comics?apikey=a449b9700ce081058880a80ef3a39512";

    public void getComics() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        String privateKey = "dfb5c85ad3cefbba7e84f48520615d8a89b45e6b";
        String publicKey = "a449b9700ce081058880a80ef3a39512";
        url = url + "&ts=" + ts + "&hash=" + md5(ts + privateKey + publicKey);
        comicBooks = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    int code = response.getInt("code");
                    if (code == 200) {
                        JSONObject dataObject = response.getJSONObject("data");
                        JSONArray results = dataObject.getJSONArray("results");

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject comicObject = results.getJSONObject(i);
                            JSONObject image = comicObject.getJSONObject("thumbnail");
                            ComicBook comicBook = new ComicBook();
                            comicBook.setDescription(comicObject.getString("description"));
                            comicBook.setTitle(comicObject.getString("title"));
                            comicBook.setThumbnailPath(image.getString("path") + "." + image.getString("extension"));
                            comicBook.setId(comicObject.getInt("id"));
                            comicBook.setDate(comicObject.getString("modified").substring(0,10));
                            System.out.print(comicBook.getThumbnailPath());
                            comicBooks.add(comicBook);
                        }
                        mListingListener.downloadFinished(comicBooks);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                System.out.print(arg0.toString());
                // TODO Auto-generated method stub

            }
        });
        queue.add(request);
    }


    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
