package com.example.milmaaishwarya.smartbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;


public class FileOpenerActivity extends AppCompatActivity {
EditText fopener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_opener);

        fopener=findViewById(R.id.editTextfopener);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        String str=intent.getStringExtra("message");
        String filename=intent.getStringExtra("filename");
        toolbar.setTitle("Smart Book");
        toolbar.setSubtitle(filename);
       fopener.setText(str);
       fopener.setEnabled(false);
    }
}
