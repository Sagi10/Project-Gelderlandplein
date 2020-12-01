package com.example.gelderlandplein.ui

import com.google.firebase.database.FirebaseDatabase

class MyFirebaseApp : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}