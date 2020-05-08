package com.example.proyecto_bar_reinols_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConexionServer extends AppCompatActivity {

    EditText editText;
    private static Socket socket;
    private static PrintWriter pw;
    private static final String IP_Server = "192.168.40.126";
    String mensaje = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_server);

        editText = (EditText) findViewById(R.id.nombre);

    }

    public void send_text(View v){
        mensaje = editText.getText().toString();
        myTask mt = new myTask();
        mt.execute();
    }

    class myTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket = new Socket(IP_Server, 5000);
                pw = new PrintWriter(socket.getOutputStream());
                pw.write(mensaje);
                pw.flush();
                pw.close();
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}