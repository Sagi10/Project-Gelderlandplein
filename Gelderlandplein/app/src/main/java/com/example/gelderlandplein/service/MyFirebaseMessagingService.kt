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
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            sendNotifiction(remoteMessage)
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Title: " + remoteMessage.notification!!.title)
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body)
            Log.d(TAG, "Message Notification URL: " + remoteMessage.notification!!.imageUrl)
        }

    }

    private fun sendNotifiction(remoteMessage: RemoteMessage) {
        // Voor aangepaste gegevens kan dit worden gebruikt.
        val data: Map<String, String> = remoteMessage.data
        val title: String = data.getValue("title")
        val content: String = data.getValue("content")
        val image: String = data.getValue("image")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 =
                NotificationChannel(CHANNEL_1_ID, "Channel 1", NotificationManager.IMPORTANCE_HIGH)
            channel1.description = "This is Channel 1"

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel1)

            val notification = NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(getBitmapFromURL(image))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_PROMO)
                .build()

            notificationManager.notify(1, notification)
        }
    }

    private fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            Log.e(TAG, "ERROR CREATING BITMAP: ${e.message}")
            null
        }
    }

    companion object {
        const val CHANNEL_1_ID = "channel1"
    }
}
