package com.tingting.ver01.profileTeamInfo.profileTeamInfoReady

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.webkit.URLUtil
import android.webkit.WebView
import android.widget.Toast
import com.tingting.ver01.R

class ChatWebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_web_view)

        var url  = intent.getStringExtra("chatUrl");
        val webView : WebView = findViewById(R.id.webView)

      //  url = url.replace(" ","")

        val checkUrl = URLUtil.isValidUrl(url)

        if(Patterns.WEB_URL.matcher(url).matches()){
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(url)

        }else{
            Toast.makeText(applicationContext,"유효하지 않은 주소입니다. 주소를 확인해주세요", Toast.LENGTH_LONG).show()
            finish()
        }


    }
}
