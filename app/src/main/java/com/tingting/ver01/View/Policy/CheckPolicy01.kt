package com.tingting.ver01.View.Policy

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.Model.CodeCallBack
import com.tingting.ver01.Model.ModelPolicy
import com.tingting.ver01.R
import kotlinx.android.synthetic.main.activity_check_policy01.*

class CheckPolicy01 : AppCompatActivity() {

    val model: ModelPolicy? = ModelPolicy(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_policy01)

        var progressBar: ProgressBar

        progressBar = findViewById(R.id.progressBar)

        model?.GetPolicyRule(object : CodeCallBack {

            override fun onSuccess(code: String, value: String) {
                super.onSuccess(code, value)
                if(code.equals("200")){
                    policy01.webViewClient = object : WebViewClient(){

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            view?.visibility = View.INVISIBLE
                            progressBar!!.visibility = View.VISIBLE
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            view?.visibility = View.VISIBLE
                            progressBar!!.visibility = View.GONE
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            errorCode: Int,
                            description: String?,
                            failingUrl: String?
                        ) {
                            super.onReceivedError(view, errorCode, description, failingUrl)
                            Toast.makeText(applicationContext, "로딩 오류", Toast.LENGTH_LONG).show()

                        }
                    }
                    //policy01.setText(HtmlCompat.fromHtml(value, 0))
                    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
                        policy01.loadData(value, "text/html", "UTF-8")
                        webviewSettings()
                    }else{
                        policy01.loadData(value, "text/html;charset=UTF-8",null)
                        webviewSettings()
                    }
                    Log.d("policy", value)
                }
                else{
                    Toast.makeText(applicationContext, "일시적인 서버 오류입니다.", Toast.LENGTH_LONG).show()
                }
            }
        })
        back.setOnClickListener {
            finish()
        }
    }

    fun webviewSettings(){
        policy01.isVerticalScrollBarEnabled= true
        policy01.requestFocus()
        policy01.settings.javaScriptEnabled = true
        policy01.settings.defaultTextEncodingName="utf-8"
        policy01.scrollBarStyle = WebView.SCROLLBARS_INSIDE_OVERLAY
        policy01.isScrollbarFadingEnabled = false
        var webSettings:WebSettings = policy01.settings
        webSettings.builtInZoomControls = true
    }

}