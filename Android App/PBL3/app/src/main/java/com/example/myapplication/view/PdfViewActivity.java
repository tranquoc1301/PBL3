package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.Observer;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebViewClient;

import com.example.myapplication.R;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.databinding.ActivityPdfViewBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;


public class PdfViewActivity extends AppCompatActivity {
    public static String STR_BUNDLE = "url";
    String url;
    ActivityPdfViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfViewBinding.inflate(getLayoutInflater());
        if (getIntent().getStringExtra(STR_BUNDLE) != null) {
            url = getIntent().getStringExtra(STR_BUNDLE);
        }
        getFileFromUrl(url);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        setContentView(binding.getRoot());
    }

    public void getFileFromUrl(String url) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL pdfURL = new URL(url);
                    pdfURL.openConnection();
                    URLConnection connection = pdfURL.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    binding.pdfView.fromStream(inputStream).load();
//                    String destination = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/temp.pdf";
//                    File file = new File(destination);
//                    OutputStream outputStream = Files.newOutputStream(file.toPath());
//                    byte[] data = new byte[1024];
//                    int count = 0;
//                    while ((count = inputStream.read(data)) != -1) {
//                        outputStream.write(data, 0, count);
//                    }
//                    outputStream.close();
//                    inputStream.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        thread.start();
    }

}