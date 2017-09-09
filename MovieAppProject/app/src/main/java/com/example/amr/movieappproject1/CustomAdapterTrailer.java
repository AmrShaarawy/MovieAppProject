package com.example.amr.movieappproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Amr on 9/10/2016.
 */
public class CustomAdapterTrailer extends ArrayAdapter<TrailerObject> {
    public CustomAdapterTrailer(Context context, ArrayList<TrailerObject> images) {
        super(context,R.layout.trailer ,images);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        if (row==null)
        {LayoutInflater layoutInflater=LayoutInflater.from(getContext());
            row=layoutInflater.inflate(R.layout.trailer,parent,false);}
        TextView textView=(TextView) row.findViewById(R.id.trailerText);
        int x=position+1;
        textView.setText("Trailer"+x);
        return row;
    }
}
