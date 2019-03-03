package com.example.milmaaishwarya.smartbook;

//import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.FileAlreadyExistsException;

public class Add_Lyrics_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button save;
    EditText addlyrics;
    private StorageReference storageRef;
    Intent intent1=new Intent(Add_Lyrics_Activity.this,HomeActivity.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__lyrics_);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        save=findViewById(R.id.button_save);
        addlyrics=findViewById(R.id.editTextadd);

        storageRef = FirebaseStorage.getInstance().getReference();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Lyrics");
        toolbar.setSubtitle("Smart Book");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(Add_Lyrics_Activity.this).create();
                alertDialog.setTitle("Save");
                alertDialog.setIcon(R.drawable.save);
                final EditText name=new EditText(Add_Lyrics_Activity.this);
                alertDialog.setView(name);
                String s="file1.txt";
                name.setText(s);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Savefile(name.getText().toString(),addlyrics.getText().toString());
                    }
                });
                alertDialog.show();
            }
        });

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startActivity(intent1);
            super.onBackPressed();
        }
    }
    public void Savefile(final String fileName, String text) {
        String state;
        state= Environment.getExternalStorageState();

            File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Smart Book Lyrics");
            if(!dir.exists()) {
                dir.mkdir();
            }
            File file=new File(dir,fileName);
            try {

                FileOutputStream fos=new FileOutputStream(file);
                fos.write(text.getBytes());
                fos.close();
                addlyrics.setText("");
                Toast.makeText(getApplicationContext(),"File "+fileName+" is saved ",Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
             String path=dir+"/"+fileName;
            final Uri fileuri = Uri.fromFile(new File(path));
            StorageReference fileRef = storageRef.child("files"+"/"+fileName);

            fileRef.putFile(fileuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            Toast.makeText(getApplicationContext(),"File "+fileName+" is saved to firebase ",Toast.LENGTH_LONG).show();

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add__lyrics_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.home) {

        } else if (id == R.id.lyrics_home) {
            Intent intent2=new Intent(Add_Lyrics_Activity.this,LyricsHomeActivity.class);
            startActivity(intent2);

        } else if (id == R.id.add_lyrics) {
            Intent intent3=new Intent(Add_Lyrics_Activity.this,Add_Lyrics_Activity.class);
            startActivity(intent3);

        } else if (id == R.id.account_settings) {

        } else if (id == R.id.connection_settings) {

        } else if (id == R.id.help) {

        }else if (id == R.id.feedback) {

        }else if (id == R.id.contact_us) {

        }else if (id == R.id.share) {

        }


        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
