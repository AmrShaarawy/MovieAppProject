package com.example.amr.movieappproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Amr on 9/12/2016.
 */
public class CustomAdapterReview extends ArrayAdapter<ReviewObject> {
  //  public ReviewObject RO=new ReviewObject();
    public CustomAdapterReview(Context context, ArrayList<ReviewObject> image) {
        super(context,R.layout.review ,image);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(R.layout.review,parent,false);
        TextView textView=(TextView)view.findViewById(R.id.reviewText);
        int x=position+1;
        textView.setText("Review"+x);
        return view;
    }
}
