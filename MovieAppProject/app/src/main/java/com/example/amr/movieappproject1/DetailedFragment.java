package com.example.amr.movieappproject1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailedFragment extends Fragment {
    Button button;
    View view;
    DataBase mydp;
    public ReviewList reviewList;
    public TrailerList trailerList;

    public String Id, Title, Poster, Relase_Date, Vote_avgrege, Overview;
    String s;
    int s1;
    DataObject dataObject;
    public DetailedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mydp = new DataBase(getContext());
        View rootView = inflater.inflate(R.layout.fragment_detailed, container, false);
        view=rootView;
        Bundle arguments = getArguments();
        try {
            if(arguments !=null){
                s = getArguments().getString("values");
                Gson gson = new Gson();
                 dataObject = gson.fromJson(s, DataObject.class);
            }
else{
                s = (String) getActivity().getIntent().getExtras().get("values");
                Gson gson = new Gson();
                 dataObject = gson.fromJson(s, DataObject.class);
            }





            Id = dataObject.id;
            Title = dataObject.title;
            Poster = dataObject.poster_path;
            Relase_Date = dataObject.release_date;
            Vote_avgrege = dataObject.vote_average;
            Overview = dataObject.overview;


            TextView title = (TextView) rootView.findViewById(R.id.title);
            title.setText(Title);
            final ImageView i = (ImageView) rootView.findViewById(R.id.poster);
            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185//" + Poster).into(i);
            TextView release_date = (TextView) rootView.findViewById(R.id.date);
            release_date.setText(" Date:" + Relase_Date);
            TextView rate = (TextView) rootView.findViewById(R.id.rate);
            rate.setText(" Rate:" + Vote_avgrege);
            TextView overview = (TextView) rootView.findViewById(R.id.overview);
            overview.setText(Overview);



            button = (Button) rootView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                   boolean isInserted = mydp.insertData(Id, Title,
                            Poster, Relase_Date, Overview, Vote_avgrege);
                    if (isInserted == true)
                        Toast.makeText(getContext(), "Saved successfully :)", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getContext(), "Movie Saved Befor :)", Toast.LENGTH_LONG).show();

                }
            });

        } catch (Exception e) {

            Toast.makeText(getActivity(), "null............... ", Toast.LENGTH_LONG);

        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            new FetchReview(this).execute(Id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
     new FetchTrailer(this).execute(Id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return rootView;}



    public void updateGUI(String s) {

        Gson gson = new Gson();
        trailerList = gson.fromJson(s, TrailerList.class);
        ArrayAdapter<TrailerObject> arrayAdapter = new CustomAdapterTrailer(getActivity(), trailerList.results);
        GridView gridView = (GridView)view.findViewById(R.id.TrailerGrid1);
        gridView.setAdapter(arrayAdapter);
        Log.i("ssss", s);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String youtube = "https://www.youtube.com/watch?v=" + trailerList.results.get(i).key;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtube)));
            }
        });
    }
    public void UpdateGui(String s)
    {
        Gson gson=new Gson();
        reviewList=gson.fromJson(s,ReviewList.class);
        ArrayAdapter<ReviewObject>reviewObjectArrayAdapter= new CustomAdapterReview(getActivity(),reviewList.results);
        GridView gridView=(GridView)view.findViewById(R.id.reviewGrid1);
        gridView.setAdapter(reviewObjectArrayAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),Review.class).putExtra("key",reviewList.results.get(i).content);
                startActivity(intent);
            }
        });


    }


    }









