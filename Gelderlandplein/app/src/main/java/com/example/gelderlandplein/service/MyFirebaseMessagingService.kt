package com.example.gelderlandplein.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.gelderlandplein.R
import com.example.gelderlandplein.helpers.UrlToBitmap
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            sendNotification(remoteMessage)
        }

    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(NOTIFICATION_CHANNEL, "Notification channel", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = "Notification channel of Gelderlandplein"

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)

            val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(remoteMessage.notification!!.title)
                .setContentText(remoteMessage.notification!!.body)
                .setLargeIcon(UrlToBitmap.getBitmapFromURL(remoteMessage.notification!!.imageUrl.toString()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_PROMO)
                .build()

            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL = "notification_channel"
        const val NOTIFICATION_ID = 1

    }
}
