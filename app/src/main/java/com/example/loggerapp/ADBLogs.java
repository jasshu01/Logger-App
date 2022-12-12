package com.example.loggerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ADBLogs extends AppCompatActivity {

    Button start, stop;
    TextView tv;
    int i = 0;
    boolean flag = true;

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adblogs);

        start = findViewById(R.id.startCapturing_radiologs);
        stop = findViewById(R.id.stopCapturing_radiologs);
        tv = (TextView) findViewById(R.id.textView_radioLogs);

        i = 0;
        flag = true;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                Log.d("mylogger", "starting");
                Toast.makeText(ADBLogs.this, "Starting", Toast.LENGTH_SHORT).show();
                capture();

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("mylogger", "stopping");
                Toast.makeText(ADBLogs.this, "Stopping", Toast.LENGTH_SHORT).show();
                flag = false;
            }
        });

    }

    public void capture() {

        final String[] str = {""};
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (flag)
                {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Runtime.getRuntime().exec("logcat - c");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            Log.e("tag", "hey , i am an  error " + i++);

                            String[] command = new String[]{"logcat", "-d", "threadtime"};
                            Process process = null;
                            try {
                                process = Runtime.getRuntime().exec(command);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            BufferedReader bufferedReader = new BufferedReader(
                                    new InputStreamReader(process.getInputStream()));
//
//
//                            StringBuilder log = new StringBuilder();

                            String line = "";

                            while (true) {
                                try {
                                    if (!((line = bufferedReader.readLine()) != null))
                                        break;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


//                                String[] myline = line.split(" ");

                                if (!line.contains("EGL_emulation"))
                                    str[0] = line + "\n\n\t" + str[0];

                            }
                            tv.setText(str[0]);
//                            str[0] = convertStreamToString(process.getInputStream()) + str[0];
//                            tv.setText(str[0]);

//                            tv.setText(bufferedReader.toString());
                        }
                    });


//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }


                }

            }
        }.start();


    }


    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String str = "";
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {

                str = line + "\n\n\t" + str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
}