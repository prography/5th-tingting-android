package com.tingting.ver01.socket

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tingting.ver01.R
import com.tingting.ver01.view.Main.MainActivity


class NotificationMessage(var context: Context){



    fun makeMessage(message:String){
        //socket의 채널 Id를 어떻게 구하지?

        val intent = Intent(context, MainActivity::class.java).apply {

            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        }
        intent.putExtra("notifiCode",99)


        val pendingIntent : PendingIntent = PendingIntent.getActivity(context,0,intent,0)

        val builder = NotificationCompat.Builder(context, "tingting")
            .setSmallIcon(R.drawable.tingting_noti_icon2)
            .setContentTitle("팅팅에서 알립니다")
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(1234, builder.build())
        }

    }

     fun createNotificationChannel(){

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name = "tingtingNotiChannel"
            val descriptionText = "tingtingNotificationChanel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("tingting", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }




}