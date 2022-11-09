package quangnvph25768.poly.demothiandroidnc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    WebView wvView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wvView =findViewById(R.id.wvView);
        Intent intent = getIntent();
        String dLink = intent.getStringExtra("link");
        wvView.loadUrl(dLink);
        wvView.setWebViewClient(new WebViewClient());
    }
}