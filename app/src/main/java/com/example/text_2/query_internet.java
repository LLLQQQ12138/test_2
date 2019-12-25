package com.example.text_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

public class query_internet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_query_internet);
        SearchView internet_input=findViewById(R.id.internet_input);
        final WebView wv_tslt=findViewById(R.id.wv_tslt);
        wv_tslt.getSettings().setJavaScriptEnabled(true);
        wv_tslt.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        internet_input.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String strURI = (query);
                strURI = strURI.trim();
                //如果查询内容为空提示
                if (strURI.length() == 0) {
                    Toast.makeText(getApplicationContext(), "查询内容不能为空!", Toast.LENGTH_LONG)
                            .show();
                }
                //否则则以参数的形式从http://dict.youdao.com/m取得数据，加载到WebView里.
                else {
                    String strURL = "https://dict.youdao.com/m/search?keyfrom=dict.mindex&q="
                            + strURI;
                    //String strURL = "http://dict-co.iciba.com/api/dictionary.php?w="+strURI+"&key=22AB6E95496FEC41C1BC83DCE43AB9CA";
                    wv_tslt.loadUrl(strURL);
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    }

