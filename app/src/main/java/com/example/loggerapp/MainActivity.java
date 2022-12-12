package com.example.loggerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {


    Button adblogs, kernelLogs, radioLogs;


    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adblogs = findViewById(R.id.adblogs);
        kernelLogs = findViewById(R.id.kernelLogs);
        radioLogs = findViewById(R.id.radioLogs);

        adblogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mylogger", "adblogs");
                Toast.makeText(MainActivity.this, "going to adb logs", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ADBLogs.class);
                startActivity(intent);
            }
        });
        kernelLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mylogger", "kernelLogs");
                Toast.makeText(MainActivity.this, "going to kernelLogs ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, KernelLogs.class);
                startActivity(intent);
            }
        });
        radioLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mylogger", "radioLogs");
                Toast.makeText(MainActivity.this, "going to radioLogs ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, RadioLogs.class);
                startActivity(intent);
            }
        });


    }

}

