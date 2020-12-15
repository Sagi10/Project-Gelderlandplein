package com.example.gelderlandplein.service

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.messaging.FirebaseMessaging

class MyFirebaseInstanceIdService: FirebaseInstanceIdService() {

    override fun onTokenRefresh() {

        // Get updated InstanceID token.
        val refreshedToken =  FirebaseMessaging.getInstance().token
        Log.d(TAG, "Refreshed token: $refreshedToken")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken)
    }
}