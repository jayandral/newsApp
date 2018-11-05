package com.example.jayand_zuch569.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class Browser extends AppCompatActivity {

    WebView browser;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_web_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        String intent = getIntent().getStringExtra("url");

        browser = (WebView) findViewById(R.id.web);
        browser.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                setTitle("Loading...");
                setProgress(progress * 100);
                if(progress == 100)
                    setTitle(R.string.app_name);
            }
        });
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl(intent);
    }
}
