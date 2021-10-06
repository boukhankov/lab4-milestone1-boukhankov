package com.example.lab4_milestone1_boukhankov;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    class ExampleRunnable implements Runnable {
        @Override
        public void run(){
            mockFileDownloader();
        }
    }

    private static final String TAG = "MainActivity";
    private Button startButton;
    private TextView downloadProgressText;
    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        downloadProgressText = findViewById(R.id.textDownloadProgress);
    }


    public void mockFileDownloader() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("DOWNLOADING...");

            }
        });


        for(int downloadProgress = 0; downloadProgress <= 100; downloadProgress += 10) {
            int finalDownloadProgress = downloadProgress;

            if(stopThread){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("START");
                        downloadProgressText.setText("");

                    }
                });
                return;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    downloadProgressText.setText("Download Progress: " + finalDownloadProgress + "%");
                }
            });


            Log.d(TAG, "Download Progress: " + downloadProgress + "%");

            try {
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("START");
                downloadProgressText.setText("");
            }
        });
    }

    public void startDownload(View view) {
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();

        //mockFileDownloader();
    }

    public void stopDownload(View view){
        stopThread = true;
    }


}