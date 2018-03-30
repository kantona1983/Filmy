package com.basha.karim.filmy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by karim on 2/7/2018.
 */

class DataAdapter extends ArrayAdapter<Model> {


    public DataAdapter(Context context, ArrayList<Model> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        if (convertView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.custom, parent, false);
        }
        Model movieApp = getItem(position);

        ImageView posterImageView = rootView.findViewById(R.id.poster_iv);
        Picasso.with(getContext()).load(movieApp.getPosterUrl()).into(posterImageView);
        return rootView;
    }

}
