package com.example.intentencionesclase4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        if(bundle != null){
            ImageView imageView = findViewById(R.id.imageCapture);
            String path = bundle.getString("path");
            imageView.setImageURI(Uri.parse(path));



            //otra forma de hacerlo con bitmap
            //Bitmap bmp;
            //if(path != null){
            //    bmp = BitmapFactory.decodeFile(path);
            //    imageView.setImageBitmap(bmp);
            }
        }
    }
