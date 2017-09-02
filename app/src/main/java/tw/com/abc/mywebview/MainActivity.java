package tw.com.abc.mywebview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView=(WebView) findViewById(R.id.webview);
        initwebView();

    }

    private void  initwebView(){
        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);

        // 開啟javascript 啟用功能
        WebSettings setting =webView.getSettings();
        setting.setJavaScriptEnabled(true);
        // 手機內部網頁
        webView.loadUrl("file:///android_asset/brad.html");
        //webView.loadUrl("file:///android_asset/mymap.html");
    }

    public void  test1(View view){

        webView.loadUrl("javascript:test2('brad')");

    }
    public void  test2(View view){
        //回到上一頁
        webView.goBack();
    }
    //=======================================
    public void  openweb(View view){
        // 用WebView開啟網頁
        webView.loadUrl("http://www.tcca.org.tw");

    }
    public void  newinit(View view){
         /*
                透過uri 開新頁面-和本主題無關
        */
        Uri uri = Uri.parse("http://www.tcca.org.tw");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);

    }
}
