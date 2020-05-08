package com.example.proyecto_bar_reinols_movil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Registrarse extends AppCompatActivity implements Serializable {

    public static FileInputStream fis;
    public static File file;
    public static byte[] byteImage;
    ImageButton img;
    EditText user, pass;
    Uri photoURI;
    static String password, nombre;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        final MediaPlayer gato_josu = MediaPlayer.create(this, R.raw.gato_josu);
        final MediaPlayer gemido_josu = MediaPlayer.create(this, R.raw.gemido_josu);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gato_josu.start();
            super.onConfigurationChanged(newConfig);
            setContentView(R.layout.activity_registrarse_landscape);
            img = findViewById(R.id.fotoCamarero);
            img.setTag(null);
            user = findViewById(R.id.nombre);
            pass = findViewById(R.id.pass);

            Button continuar = findViewById(R.id.continuar);
            continuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.getText().toString().isEmpty() && pass.getText().toString().isEmpty()) {
                        Toast.makeText(Registrarse.this, "Usuario y Password no valido.", Toast.LENGTH_SHORT).show();
                    }else if (user.getText().toString().isEmpty()) {
                        Toast.makeText(Registrarse.this, "Usuario no valido.", Toast.LENGTH_SHORT).show();
                    }else if (pass.getText().toString().isEmpty()) {
                        Toast.makeText(Registrarse.this, "Password no valido.", Toast.LENGTH_SHORT).show();
                    } else if (img.getTag() == null) {
                        Toast.makeText(Registrarse.this, "Falta la imagen.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Registrarse.this, "Camarero añadido.", Toast.LENGTH_SHORT).show();
                        Login.comienzoDeAplicacion = false;
                        Intent intent = new Intent(Registrarse.this, Login.class);
                        startActivity(intent);
                    }
                }
            });

            ImageButton foto = findViewById(R.id.fotoCamarero);
            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.getText().toString().isEmpty()) {
                        Toast.makeText(Registrarse.this, "Primero el usuario.", Toast.LENGTH_SHORT).show();
                    }else {
                        tomarFoto(img);
                    }
                }
            });
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gemido_josu.start();
            super.onConfigurationChanged(newConfig);
            setContentView(R.layout.activity_registrarse);
            img = findViewById(R.id.fotoCamarero);
            img.setTag(null);
            user = findViewById(R.id.nombre);
            pass = findViewById(R.id.pass);

            Button continuar = findViewById(R.id.continuar);
            continuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.getText().toString().isEmpty() && pass.getText().toString().isEmpty()) {
                        Toast.makeText(Registrarse.this, "Usuario y Password no valido.", Toast.LENGTH_SHORT).show();
                    }else if (user.getText().toString().isEmpty()) {
                        Toast.makeText(Registrarse.this, "Usuario no valido.", Toast.LENGTH_SHORT).show();
                    }else if (pass.getText().toString().isEmpty()) {
                        Toast.makeText(Registrarse.this, "Password no valido.", Toast.LENGTH_SHORT).show();
                    } else if (img.getTag() == null) {
                        Toast.makeText(Registrarse.this, "Falta la imagen.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Registrarse.this, "Camarero añadido.", Toast.LENGTH_SHORT).show();
                        Login.comienzoDeAplicacion = false;
                        Intent intent = new Intent(Registrarse.this, Login.class);
                        startActivity(intent);
                    }
                }
            });

            ImageButton foto = findViewById(R.id.fotoCamarero);
            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.getText().toString().isEmpty()) {
                        Toast.makeText(Registrarse.this, "Primero el usuario.", Toast.LENGTH_SHORT).show();
                    }else {
                        tomarFoto(img);
                    }
                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int oreientacion = getResources().getConfiguration().orientation;
        if (oreientacion == Configuration.ORIENTATION_LANDSCAPE){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registrarse_landscape);
        } else  {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registrarse);
        }

        if (ContextCompat.checkSelfPermission(Registrarse.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Registrarse.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Registrarse.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        img = findViewById(R.id.fotoCamarero);
        img.setTag(null);
        user = findViewById(R.id.nombre);
        pass = findViewById(R.id.pass);

        Button continuar = findViewById(R.id.continuar);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().isEmpty() && pass.getText().toString().isEmpty()) {
                    Toast.makeText(Registrarse.this, "Usuario y Password no valido.", Toast.LENGTH_SHORT).show();
                }else if (user.getText().toString().isEmpty()) {
                    Toast.makeText(Registrarse.this, "Usuario no valido.", Toast.LENGTH_SHORT).show();
                }else if (pass.getText().toString().isEmpty()) {
                    Toast.makeText(Registrarse.this, "Password no valido.", Toast.LENGTH_SHORT).show();
                } else if (img.getTag() == null) {
                    Toast.makeText(Registrarse.this, "Falta la imagen.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Registrarse.this, "Camarero añadido.", Toast.LENGTH_SHORT).show();
                    Login.comienzoDeAplicacion = false;
                    Intent intent = new Intent(Registrarse.this, Login.class);
                    startActivity(intent);
                }
            }
        });

        ImageButton foto = findViewById(R.id.fotoCamarero);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().isEmpty()) {
                    Toast.makeText(Registrarse.this, "Primero el usuario.", Toast.LENGTH_SHORT).show();
                }else {
                    tomarFoto(img);
                }
            }
        });

    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yy-MM-dd").format(new Date());
        //String imageFileName = user.getText().toString();
        String imageFileName = timeStamp + "_" + user.getText().toString() + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private String imgPath;
    static final int REQUEST_TAKE_PHOTO = 2;
    public void tomarFoto(View view) {
        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.proyecto_bar_reinols_movil",
                        photoFile);
                imgPath = photoFile.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public String getImagePath() {
        return imgPath;
    }

    static final int CAMERE_REQUEST = 2;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERE_REQUEST && resultCode == RESULT_OK) {
            img.setTag("otros");
            img.setImageURI(photoURI);
            rotarImagen(img);
            password = encriptPassword();
            nombre = user.getText().toString();

            String ruta = photoURI.toString();
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            String[] fileName = ruta.split("/");
            file = new File(storageDir + File.separator + fileName[fileName.length-1]);

            try {
                byteImage = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ConexionServer conServerGuardarImagen = new ConexionServer();
            conServerGuardarImagen.queConsultaHacer = "guardar_imagen";
            conServerGuardarImagen.task(Registrarse.this);
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void rotarImagen(ImageView imagen){
        try {
            ExifInterface exif = new ExifInterface(getImagePath());
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (rotation) {
                case 6:
                    imagen.setRotation(90);
                    break;
                case 3:
                    imagen.setRotation(180);
                    break;
                case 1:
                    imagen.setRotation(0);
                    break;
                case 8:
                    imagen.setRotation(-90);
                    break;
            }
                /*left 1,
                bottom 8,
                right 3,
                top 6*/
        } catch (IOException e){}
    }

    private String encriptPassword(){
        byte[] md5Input = pass.getText().toString().getBytes();
        BigInteger md5Data = null;
        try {
            md5Data = new BigInteger(1, encryptMD5(md5Input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String md5String = md5Data.toString(32);

        return md5String;
    }

    private static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }
}
