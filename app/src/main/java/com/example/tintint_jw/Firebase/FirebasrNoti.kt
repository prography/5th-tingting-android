/*package com.example.tintint_jw.Firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.tintint_jw.R
import com.example.tintint_jw.View.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.android.synthetic.main.recyclerview_team_info.view.*

class FirebasrNoti : FirebaseMessagingService() {

    private val TAG = "FirebaseService"


    //새로운 토큰 생성.
    override fun onNewToken(token: String?) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        if(remoteMessage!!.notification!=null){
            Log.d(TAG, "Notification Message Body: ${remoteMessage.notification?.body}")
            sendNotification(remoteMessage.notification?.body)

        }
        super.onMessageReceived(remoteMessage)
    }

    private fun sendNotification(body: String?){
        val intent = Intent(this,MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("Notificaiton", body)

        }
            val CHANNEL_ID = "CollocNotification"
            val CHANNEL_NAME = "CollocChannel"
            val description = "This is Colloc channel"
            val importance = NotificationManager.IMPORTANCE_HIGH;

        var notificationManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.setShowBadge(false)
            notificationManager.createNotificationChannel(channel)
        }


        var pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        var notificationSound =RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //builder에서
        var notificationBuilder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.plus)
            .setContentTitle("Push noticiation FCM")
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(notificationSound)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0, notificationBuilder.build())

    }
}*/