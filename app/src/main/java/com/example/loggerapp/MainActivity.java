package com.example.loggerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {


    Button start, stop;
    TextView tv;
    int i = 0;
    boolean flag = true;

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.startCapturing);
        stop = findViewById(R.id.stopCapturing);
        tv = (TextView) findViewById(R.id.textView);

        i = 0;
        flag = true;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=true;
                capture();

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
            }
        });

    }


    public void capture() {


        new Thread() {
            @Override
            public void run() {
                super.run();
                while (flag) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {


                                Runtime.getRuntime().exec("logcat - c");

//                Log.e("tag", "hey , i am an error "+ i);
                                Log.e("tag", "hey , i am an error " + i++);
//            Process process = Runtime.getRuntime().exec("logcat");


                                String[] command = new String[]{"logcat", "-d", "-v", "threadtime"};
                                Process process = Runtime.getRuntime().exec(command);


                                BufferedReader bufferedReader = new BufferedReader(
                                        new InputStreamReader(process.getInputStream()));

                                StringBuilder log = new StringBuilder();
                                String str = "";
                                String line = "";
                                while ((line = bufferedReader.readLine()) != null) {

                                    str = line + "\n\n\t" + str;
                                    log.append(line + " " + i);
                                    log.append("\n\n\t");

                                }

                                tv.setText(str);

                            } catch (IOException e) {

                                tv.setText(e.toString());
                                Log.e("tag", e.toString() + " " + e.getMessage() + " " + e.getLocalizedMessage());
                                e.printStackTrace();
                            }
                        }

                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

            }
        }.start();


    }
}

