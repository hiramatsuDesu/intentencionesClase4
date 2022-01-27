package com.example.intentencionesclase4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> itemList;

    //constructor
    ImageAdapter (Context context){
        this.context=context;
        this.itemList=new ArrayList<>();
    }

    //agrega la imagen
    public void AddPath(String path){
        itemList.add(path);
    }

    //libera la memoria
    public void Clear(){
        itemList.clear();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null){
            imageView = new ImageView(this.context);
            //imageView.setLayoutParams(new ViewGroup.LayoutParams(200,200));

            //no recomendado
            //imageView.setPadding(8,8,8,8);
            //imageView.setImageURI(Uri.parse(this.getItem(i).toString()));

        }else {
            imageView=(ImageView)view;
        }

        //cargar vista previa recomendado
        String bitmapPath = this.itemList.get(i);
        Bitmap bitmap = decodeSampleBitmap(bitmapPath, 200, 200);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        imageView.setImageBitmap(bitmap);
        
        return imageView;
    }

    private Bitmap decodeSampleBitmap(String bitmapPath, int width, int heigth) {
        Bitmap bmp = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        //sube la bandera
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(bitmapPath, options);

        options.inSampleSize = calculateSampleSize(options, width, heigth);

        //baja la bandera
        options.inJustDecodeBounds = false;

        bmp = BitmapFactory.decodeFile(bitmapPath, options);

        return bmp;
    }

    private int calculateSampleSize(BitmapFactory.Options options, int width, int heigth) {
        int inSampleSize =1;

        //obtengo el ancho real
        int optionsWidth=options.outWidth;

        //obtengo el alto real
        int optionsHeigth = options.outHeight;

        //si cualquiera de las dimensiones reales sobrepasa los 200 que habia establecido
        //sera reestablecido el tamaño de la imagen proporcionalmente
        if(optionsWidth > width || optionsHeigth > heigth){

            //contemplara la menor dimension para reducir
            if(optionsWidth > optionsHeigth){
                //si el ancho es mayor al alto se reajusta al alto
                inSampleSize = Math.round((float)optionsHeigth/(float)heigth);
            }else{
                // si el ancho es menor que el alto reajusta el ancho
                inSampleSize = Math.round((float) optionsWidth/(float)width);
            }
        }
        return inSampleSize;
    }
}



