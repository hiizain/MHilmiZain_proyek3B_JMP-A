package com.example.myapplicationpertemuan9b;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File folder = new File("/data/data/com.example.myapplicationpertemuan9b/files");
        File[] listOfFiles = folder.listFiles();

        ArrayList<String> alFile = new ArrayList<>();
        String[] files = new String[listOfFiles.length];
        int i=0;

        for (File file : listOfFiles) {
            if (file.isFile()) {
                alFile.add(file.getName());
                files[i] = file.getName();
                i++;
            }
        }

//        ArrayList<String> listOfFiles = new ArrayList<>(folder.listFiles());
        ListView lv_catatan = findViewById(R.id.lvCatatan);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,alFile);
        lv_catatan.setAdapter(adapter);

        lv_catatan.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l){
                showDialog(files[i]);
            }
        });

        FloatingActionButton b_add = findViewById(R.id.bAdd);

        b_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahCatatanActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showDialog(String filename){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Hapus catatan ini?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda yakin akan menghapus catatan : "+filename+"?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        hapusCatatan(filename);
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    void hapusCatatan(String filename){
        File file = new File(getFilesDir(), filename);
        if(file.exists()){
            file.delete();
        }
    }
}