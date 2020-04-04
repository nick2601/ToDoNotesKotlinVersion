package com.demonhunter.todonoteskotlinversion.fcm
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.demonhunter.todonoteskotlinversion.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService:FirebaseMessagingService() {
    private val TAG ="FirebaseMessagingService"
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        setupNotification(message.notification?.body)
    }

    private fun setupNotification(body: String?) {  //channelId is required whenever we are using notifications from android O and above
        val channelId = "Todo ID"
        val ringtone =RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder= NotificationCompat.Builder(this,channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("ToDo Notes App")
            .setContentText(body)
            .setSound(ringtone)
        val notificationManager= getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager //here we are getting the service of the system of this context
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O){       //here a manual check is done whether phone api level is greater than oreo
            val channel =NotificationChannel(channelId,"Todo-Notes",NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0,notificationBuilder.build())
    }

    override fun onNewToken(token: String) {   // this is to send message only at your end and not others
        super.onNewToken(token)                //the token in Splash actvity and this always same
    }
}