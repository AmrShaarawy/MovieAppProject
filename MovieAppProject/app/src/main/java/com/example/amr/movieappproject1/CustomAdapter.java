package com.example.amr.movieappproject1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Amr on 8/28/2016.
 */
public class CustomAdapter extends ArrayAdapter<DataObject> {

    public CustomAdapter(Context context, ArrayList<DataObject> images) {
        super(context,R.layout.image ,images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;

            LayoutInflater layoutInflater=LayoutInflater.from(getContext());
            row=layoutInflater.inflate(R.layout.image,parent,false);

        ImageView imageView=(ImageView)row.findViewById(R.id.images);
        DataObject s=getItem(position);
        Log.i("dataaaa ", "getView: "+s.poster_path);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185//"+s.poster_path).into(imageView);
        return row;
    }
}
