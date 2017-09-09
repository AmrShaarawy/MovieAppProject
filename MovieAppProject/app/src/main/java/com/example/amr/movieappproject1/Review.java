package com.example.amr.movieappproject1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Review extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        TextView textView = (TextView)findViewById(R.id.reviewtext1);
        String s=getIntent().getStringExtra("key");
        textView.setText(s);
    }
}
