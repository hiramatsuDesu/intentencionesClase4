package com.example.intentencionesclase4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;

public class PhotoGallery extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ImageAdapter imageAdapter;
    private static final int REQUEST_ID=0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        //leer las rutas de las imagenes


        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    REQUEST_ID);
            return;
        }
        loadImageGallery();
    }

    private void loadImageGallery() {
        imageAdapter = new ImageAdapter(this);
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        String externalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();

        externalStorage += File.separator + "Camera" + File.separator;

        //leer el directorio donde esta la imagen
        File targetDir = new File(externalStorage);

        //leer todos los archivos en una lista
        File[] files = targetDir.listFiles();

        if(files != null){
            //vamos a recorrer toda la lista con un for
            for (File file: files){
                //cargamos la lista de fotos en el imageAdapter
                imageAdapter.AddPath(file.getAbsolutePath());
            }
        }

        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_ID)
        {
            if(permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Permiso no concedido", Toast.LENGTH_SHORT).show();
                }else{
                    loadImageGallery();
                }
            }

            if(permissions[1].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                if(grantResults[1] != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Permiso no concedido", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String path = imageAdapter.getItem(position).toString();
        Intent intentShow = new Intent(this, ShowImage.class);

        //enviar varios datos extras a un intent a traves de un bundle
        Bundle bundle = new Bundle();
        bundle.putString("path", path);

        intentShow.putExtras(bundle);
        startActivity(intentShow);
    }
}