package com.example.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

class FCMService: FirebaseMessagingService() {

    companion object{
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "notification_channel"
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        Log.i("Token", p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        val data = p0.data["data"]

        val jObj = JSONObject(data!!)

        val title = jObj.getString("title")
        val message = jObj.getString("message")

        val payload = jObj.getString("payload")

        Log.i("TOKEN", "data : $data, payload : $payload")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        showNotification(title, message)
    }

    private fun showNotification(title: String?, message: String?){

        val notificationSoundUri = Uri.parse("android.resource://${applicationContext.packageName}/${R.raw.eventually}")

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(notificationSoundUri)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channel.setShowBadge(true)
            notificationBuilder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationBuilder.color = ContextCompat.getColor(this, android.R.color.white)
        val notification = notificationBuilder.build()
        notificationManager.notify(0, notification)

    }
}