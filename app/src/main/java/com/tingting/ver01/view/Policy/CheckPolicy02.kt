package com.tingting.ver01.view.Policy

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelPolicy
import com.tingting.ver01.R
import kotlinx.android.synthetic.main.activity_check_policy02.*
import kotlinx.android.synthetic.main.activity_check_policy02.back

class CheckPolicy02 : AppCompatActivity() {

    val model: ModelPolicy? = ModelPolicy(this)


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_policy02)
        var vc:Int = Color.parseColor("#909592")
        var progressBar:ProgressBar

        progressBar = findViewById(R.id.progressBar)

        model?.GetPolicyPrivate(object : CodeCallBack {

            override fun onSuccess(code: String, value: String) {
                super.onSuccess(code, value)
                if(code.equals("200")){
                    policy02.webViewClient = object : WebViewClient(){

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            view?.visibility = View.INVISIBLE
                            progressBar.visibility = View.VISIBLE
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            view?.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
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

                    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
                        policy02.loadData(value, "text/html", "UTF-8")
                        webviewSettings()
                    }else{
                        policy02.loadData(value, "text/html;charset=UTF-8",null)
                        webviewSettings()
                    }
                    //Log.d("policy2", value)
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
        policy02.isVerticalScrollBarEnabled = true
        policy02.requestFocus()
        policy02.settings.javaScriptEnabled = true
        policy02.settings.defaultTextEncodingName="utf-8"

    }
}