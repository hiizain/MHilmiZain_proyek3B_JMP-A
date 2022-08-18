package com.example.myapplicationpertemuan9b;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TambahCatatanActivity extends AppCompatActivity {

    EditText et_judul, et_catatan;

    Button b_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_catatan);

        et_judul = findViewById(R.id.etJudul);
        et_catatan = findViewById(R.id.etCatatan);

        b_simpan = findViewById(R.id.bSimpan);

        b_simpan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (simpanFileData()){
                    Intent intent = new Intent(TambahCatatanActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(TambahCatatanActivity.this, "Data gagal disimpan!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean simpanFileData(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime japanDateTime = now.withZoneSameInstant(ZoneId.of("Asia/Jakarta"));
        String tanggal = dtf.format(japanDateTime);
        String isiFile = et_judul.getText().toString()+";"+
                et_catatan.getText().toString()+";"+
                tanggal;
        File file = new File(getFilesDir(), et_judul.getText().toString());
        FileOutputStream fileOutputStream = null;

        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file, false);
            fileOutputStream.write(isiFile.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}