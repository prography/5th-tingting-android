package com.tingting.ver01.sharedPreference

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import com.google.firebase.analytics.FirebaseAnalytics
import com.kakao.usermgmt.StringSet
import com.kakao.util.helper.Utility
import com.tingting.ver01.view.GlideImage
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class App :Application(){
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    companion object{
        lateinit var instance: App
        lateinit var prefs : SharedPreference
    }

    override fun onCreate() {
        super.onCreate()
        prefs = SharedPreference(applicationContext)

        instance = this

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()

        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, App.prefs.mylocal_id.toString())
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, StringSet.name)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        GlideImage.sign++

//        SendBirdUIKit.init(object : SendBirdUIKitAdapter{
//            override fun getUserInfo(): UserInfo {
//
//                return object : UserInfo{
//                    override fun getUserId(): String {
//                       return App.prefs.myToken.toString()
//                    }
//
//                    override fun getNickname(): String {
//                      return App.prefs.myId.toString()
//                    }
//
//                    override fun getProfileUrl(): String {
//                        return App.prefs.mythumnail.toString()
//                    }
//                }
//            }
//
//            override fun getAppId(): String {
//                return "02882A77-2267-4416-9682-62DED862B795"
//            }
//
//            override fun getAccessToken(): String {
//               return ""
//            }
//        },this)


    }

    fun getHashKey(context: Context): String? {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                val packageInfo =
                    Utility.getPackageInfo(context, PackageManager.GET_SIGNING_CERTIFICATES)
                val signatures = packageInfo.signingInfo.apkContentsSigners
                val md = MessageDigest.getInstance("SHA")
                for (signature in signatures) {
                    md.update(signature.toByteArray())
                    return String(Base64.encode(md.digest(), Base64.NO_WRAP))
                }
            } else {
                val packageInfo =
                    Utility.getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null

                for (signature in packageInfo!!.signatures) {
                    try {
                        val md = MessageDigest.getInstance("SHA")
                        md.update(signature.toByteArray())
                        return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
                    } catch (e: NoSuchAlgorithmException) {

                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return null
    }


}

