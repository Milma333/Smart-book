package com.example.milmaaishwarya.smartbook;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

class ProgressBarAnimation extends Animation {

    private Context context;
    private ProgressBar progressbar;
    private TextView textview;
    private float from;
    private float to;
    ProgressBarAnimation(Context context, ProgressBar progressBar, TextView textView, float from, float to){
        this.context=context;
        this.progressbar=progressBar;
        this.textview=textView;
        this.from=from;
        this.to=to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value=from+(to-from)*interpolatedTime;
        progressbar.setProgress((int)value);
        textview.setText(String.valueOf((int)value+" %"));

        if(value==to){
            context.startActivity(new Intent(context, SignupActivity.class));
        }

    }
}
