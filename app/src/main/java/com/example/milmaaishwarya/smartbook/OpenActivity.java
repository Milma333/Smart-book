package com.example.milmaaishwarya.smartbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class OpenActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        progressBar=findViewById(R.id.prog_bar);
        textView=findViewById(R.id.text_view);
        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        ProgressBarAnimation();
    }
   private void ProgressBarAnimation (){
        ProgressBarAnimation anim= new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
        anim.setDuration(1000);
        progressBar.setAnimation(anim);

    }
}

