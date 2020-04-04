package com.demonhunter.todonoteskotlinversion.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.demonhunter.todonoteskotlinversion.R
import com.demonhunter.todonoteskotlinversion.onboarding.OnBoardingActivity
import com.demonhunter.todonoteskotlinversion.utils.PrefConst
import com.demonhunter.todonoteskotlinversion.utils.StoreSession
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId


class SplashActivity : AppCompatActivity() {

    val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreference()
        checkLoginStatus()
        getFCMToken()
        setupNotification("This is Local Notification")

    }

    private fun setupNotification(body: String?) {  //channelId is required whenever we are using notifications from android O and above
        val channelId = "Local ID"
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("ToDo Notes App")
            .setContentText(body)
            .setSound(ringtone)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager //here we are getting the service of the system of this context
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {       //here a manual check is done whether phone api level is greater than oreo
            val channel =
                NotificationChannel(channelId, "Todo-Notes", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast

                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            })
    }


    private fun setupSharedPreference() {
        StoreSession.init(this)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = StoreSession.read(PrefConst.IS_LOGGED_IN)
        val isBoardingSuccess =
            StoreSession.read(PrefConst.ON_BOARDED_SUCCESSFULLY)

        if (isLoggedIn!!) {
            val intent = Intent(
                this@SplashActivity,
                MyNotesActivity::class.java
            )
            startActivity(intent)
        }
        else {
    //                if onBoarded success ->loginActivity   else->onBoardingActivity
            if (isBoardingSuccess!!) {
                val intent = Intent(
                    this@SplashActivity,
                    LoginActivity::class.java
                )
                startActivity(intent)
            }
            else {
                val intent = Intent(
                    this@SplashActivity,
                    OnBoardingActivity::class.java
                )
                startActivity(intent)
            }

        }
        finish()    //while designing a splash activity we need to definitely add a finish() to the current activity

    }
}
//last edit is to remove the boiler plate code and we stored that in a singular place