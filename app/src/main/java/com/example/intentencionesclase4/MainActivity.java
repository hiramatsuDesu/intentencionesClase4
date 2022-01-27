package com.example.intentencionesclase4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //creamos una constante estatica y predefinida
    public static final Integer REQUEST_ID=1234;
    private static final int INTENT_RESULT = 5678;
    private static final int CAPTURE_RESULT = 9876;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permiso estas lineas no van en el onCreate
        //if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
        //    requestPermissions(new String[] { Manifest.permission.CALL_PHONE },
        //            REQUEST_ID);
        //}
        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_ID)
        {
            if(permissions[0].equals(Manifest.permission.CALL_PHONE))
            {
                Button btnPhone = findViewById(R.id.btnPhone);
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    btnPhone.setEnabled(true);

                    //se anula el color gris al denegar permiso
                    onBtnPhoneClick(null);
                }else{
                    //si le ponemos false va a preguntar 1 vez
                    btnPhone.setEnabled(true);
                }
            }
        }

    }

    public void onBtnWebClick(View view){
        //boton abrir pag web
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
        startActivity(intent);

        //ahora vamos al activiyt_main a crear el onclick
        //y pedir permiso para internet en el manifest
    }

    public void onBtnPhoneClick(View view){
        //estas lineas van aqui
        if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[] { Manifest.permission.CALL_PHONE }, REQUEST_ID);
            return;
        }



        //recordar el onclick y el manifest para pedir permiso

        //vamos a extraer el nro
        EditText edPhoneNumber = findViewById(R.id.edPhone);
        String phoneNumber = edPhoneNumber.getText().toString();

        //llamamos al intent y le pasamos el phoneNumber
        //para hacer llamada es Action_Call sino seguira accediendo solo al discador
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + phoneNumber));

        //inicia la actividad
        startActivity(intent);
    }



    //lee preferencia
    public void btnSettingsClick(View view){
        Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
        startActivityForResult(settings, INTENT_RESULT);
    }

    //muestra preferencia
    //en la misma estructura photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==INTENT_RESULT){
            //vamos usar un content provider para mostrar los datos
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences
                    (this);
            boolean aboutPref = sharedPreferences.getBoolean(SettingsActivity.KEY_SHOW_ABOUT_PREFERENCE,
                    false);

            Toast.makeText(this, "Settings show about is: " + aboutPref,
                    Toast.LENGTH_LONG).show();
        }

        //photo
        if(requestCode==CAPTURE_RESULT){
            loadImage(data);
        }

    }

    private void loadImage(Intent data) {
        Bundle extras = data.getExtras();
        if(extras != null){
            Bitmap imageBmp = (Bitmap) extras.get("data");
            ImageView imageView = findViewById(R.id.imgView);
            imageView.setImageBitmap(imageBmp);
        }else{
            Toast.makeText(this, "No hay imagen", Toast.LENGTH_SHORT).show();
        }
    }

    //google maps
    public void onBtnMapClick(View view){
        Intent intentMap = new Intent( Intent.ACTION_VIEW, Uri.parse("geo: -31.4168272,-64.1857908"));
        startActivity(intentMap);

        Toast.makeText(this, "Plaza San Martin", Toast.LENGTH_SHORT).show();
    }


    //tomar foto
    public void onBtnPhotoClick(View view){
        Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intentPhoto, CAPTURE_RESULT);
    }


    public void onBtnEmailClick(View view) {
        Uri uriMail = Uri.parse("mailto:");
        Intent intentMail = new Intent(Intent.ACTION_SENDTO, uriMail);

        intentMail.setType("text/html");
        intentMail.setData(uriMail);
        intentMail.putExtra(Intent.EXTRA_SUBJECT, "Nuevo asunto del mail");
        intentMail.putExtra(Intent.EXTRA_EMAIL, new String[]{"pedro@picapiedra.com", "pablo@marmol.com"});
        intentMail.putExtra(Intent.EXTRA_CC, new String[]{"vilma@picapiedra.com"});
        intentMail.putExtra(Intent.EXTRA_BCC, new String[]{"betty@marmol.com"});
        intentMail.putExtra(Intent.EXTRA_TEXT, "<p>En medio de tanto basureo constante me he " +
                "atrevido a soñar... Dios protege mi atrevimiento...</p>");

        startActivity(intentMail);
    }

    public void onBtnPhotoGalleryClick(View view){
        Intent intentGallery = new Intent(MainActivity.this, PhotoGallery.class);
        startActivity(intentGallery);
    }

}