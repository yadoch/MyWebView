package tw.com.abc.mywebview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URI;


public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private LocationManager lmgr;
    private MyListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,

                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED

                ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
        } else {
            init();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.webview);
        initwebView();

        lmgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new MyListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
    }

    @Override
    public void finish() {
        //關閉程式時,關閉listener
        lmgr.removeUpdates(listener);
        super.finish();
    }

    private class MyListener implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            double lat =location.getLatitude();
            double lng = location.getLongitude();
            webView.loadUrl("javascript:moveTo("+lat+","+lng+")");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
    private void  initwebView(){
        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);

        // 開啟javascript 啟用功能
        WebSettings setting =webView.getSettings();
        setting.setJavaScriptEnabled(true);

        // 自訂 javascrit 類別
        webView.addJavascriptInterface(new MyJS(),"brad");
        // 手機內部網頁-配合test1()
        webView.loadUrl("file:///android_asset/brad.html");

        // Google map
       // webView.loadUrl("file:///android_asset/mymap.html");
    }

    public void  test1(View view){

        webView.loadUrl("javascript:test2('brad')");

    }
    public void  test2(View view){
        //回到上一頁
        webView.goBack();
    }

    public class MyJS{
        @JavascriptInterface
        public String m1(){
            Log.i("geoff","m1()");
            return "";
        }
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
