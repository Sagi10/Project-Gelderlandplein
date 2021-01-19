package com.example.gelderlandplein.viewmodel

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gelderlandplein.models.Art
import com.example.gelderlandplein.models.Event
import com.example.gelderlandplein.models.Shop
import com.example.gelderlandplein.repository.FirebaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class FirebaseViewModel(application: Application) : AndroidViewModel(application) {
    private val firebaseRepository: FirebaseRepository = FirebaseRepository()

    val shops: LiveData<ArrayList<Shop>> = firebaseRepository.shops
    val events: LiveData<ArrayList<Event>> = firebaseRepository.events
    val arts: LiveData<ArrayList<Art>> = firebaseRepository.arts
    val lastSeenShops: LiveData<ArrayList<Shop>> = firebaseRepository.lastSeenShops

    private val _viewedShop: MutableLiveData<String> = MutableLiveData()
    private val _shopDetail: MutableLiveData<Shop> = MutableLiveData()
    private val _eventDetail: MutableLiveData<Event> = MutableLiveData()
    private val _artsDetail: MutableLiveData<Art> = MutableLiveData()

    val shopDetail: LiveData<Shop>
        get() = _shopDetail

    val eventDetail: LiveData<Event>
        get() = _eventDetail

    val artDetail: LiveData<Art>
        get() = _artsDetail

    val viewedShop: LiveData<String>
        get() = _viewedShop

    fun sendLastViewed(shop: String?){
        viewModelScope.launch {
            try {
                _viewedShop.value = shop
            } catch (e: Throwable) {
                Log.e(TAG, "ERROR MET VERSTUREN LAST SEEN SHOP: ${e.message}")
            }
        }
    }

    fun sendDetailShop(shopDetail: Shop) {
        try {
            _shopDetail.value = shopDetail
        } catch (e: Throwable) {
            Log.e(TAG, "ERROR MET VERSTUREN DETAIL SHOP: ${e.message}")
        }
    }

    fun sendDetailEvent(eventDetail: Event) {
        try {
            _eventDetail.value = eventDetail
        } catch (e: Throwable) {
            Log.e(TAG, "ERROR MET VERSTUREN DETAIL EVENT: ${e.message}")
        }
    }

    fun sendDetailArt(artDetail: Art) {
        try {
            _artsDetail.value = artDetail
        } catch (e: Throwable) {
            Log.e(TAG, "ERROR MET VERSTUREN DETAIL ART: ${e.message}")
        }
    }

    fun sendShopList(shopList: ArrayList<String>) {
        try {
            firebaseRepository.getLastSeenShops(shopList)
        } catch (e: Throwable) {
            Log.e(TAG, "ERROR MET VERSTUREN VAN SHOP LIST: ${e.message}")
        }
    }
}