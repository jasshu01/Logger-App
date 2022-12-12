package com.example.loggerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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

public class RadioLogs extends AppCompatActivity {

    Button start, stop;
    TextView tv;
    int i = 0;
    boolean flag = true;
    String finallogs = "";

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_logs);

        start = findViewById(R.id.startCapturing_radiologs);
        stop = findViewById(R.id.stopCapturing_radiologs);
        tv = findViewById(R.id.textView_radioLogs);

//        tv.setText("hellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhel \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \nhellllo \n");

        i = 0;
        flag = true;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
//                Toast.makeText(RadioLogs.this, "Starting", Toast.LENGTH_SHORT).show();
                start.setText("Capturing Logs");


                new Thread() {
                    @Override
                    public void run() {
                        super.run();

//                        while (flag)
                        {
                            try {
                                capture();
//                                new MyTask().execute();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                    }
                }.start();


            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag = false;
//                Toast.makeText(RadioLogs.this, "Stopping", Toast.LENGTH_SHORT).show();
                start.setText("Start");
            }
        });

    }

    private void capture() throws Exception {

        new MyTask().execute();

    }

    class MyTask extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... iss) {

            String[] command = new String[]{"logcat", "radio", "threadtime"};
            Process process = null;

            try {
                Runtime.getRuntime().exec("logcat - c");
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                process = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String data = "";
            String line = "";

            while (true) {


                try {
                    if (!((line = bufferedReader.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!line.contains("EGL_emulation")) {
                    data = line + "\n\n";
                    publishProgress(data);

                }

//            tv.setText(data);
//            Log.d("mylogger","while "+ line);
            }
            return data;
        }


        @Override
        protected void onProgressUpdate(String... values) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(values[0] + tv.getText());
                }
            });
        }

        @Override
        protected void onPostExecute(String data) {
            Log.d("mylogger", "done");
            tv.setText(data);
        }
    }

    public void capture1() {
        final String[] str = {""};

        new Thread() {
            @Override
            public void run() {
                super.run();
//                while (flag)
                {


//                                          Log.e("tag", "hey , i am an radio error " + i++);

                    String[] command = new String[]{"logcat", "-d", "radio", "threadtime"};
                    Process process = null;
                    try {
                        process = Runtime.getRuntime().exec(command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    new MyTask1().execute(process.getInputStream());
                    tv.setText(finallogs);
                }
//                                  }
//                    );

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                try {
                    Runtime.getRuntime().exec("logcat - c");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }

    class MyTask1 extends AsyncTask<InputStream, Integer, String> {
        String data = "";

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(InputStream... params) {
            data = "";
            try {

                InputStream stream = params[0];
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(stream));


//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
                String line = "";
                while (true) {
                    try {
                        if (!((line = bufferedReader.readLine()) != null && flag)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


//                            Log.d("mylogger", "while " + line);
                    data = line + "\n\n\t" + data;

//                    onProgressUpdate();
//                    publishProgress(Integer.valueOf(data));
                    tv.setText(data);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
//                    }
//                });


            } catch (Exception e) {
                // do something
            }
            return data;
        }

        @Override
        protected void onPostExecute(String data) {

            Log.d("mylogger", " post  " + data);
            tv.setText(data);
        }


//        protected void onProgressUpdate() {
//            //...
//
////            Log.d("mylogger", " progress " + progress);
////            Log.d("mylogger", " progress ");
////            tv.setText(data);
////            finallogs = progress;
////            TextView tv1 = getParent().findViewById(R.id.textView_radioLogs);
////            tv1.setText(progress);
//
//        }


    }

}