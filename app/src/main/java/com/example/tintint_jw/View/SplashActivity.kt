package com.example.tintint_jw.View

import android.R.id
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import com.google.firebase.analytics.FirebaseAnalytics


class SplashActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000 //3 seconds
    private lateinit var  mFirebaseAnalytics: FirebaseAnalytics
    internal val mRunnable: Runnable = Runnable {

            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R    .layout.activity_splash)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "aa")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"bb")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }

}
