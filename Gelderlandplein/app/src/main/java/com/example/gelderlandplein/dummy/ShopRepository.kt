package com.example.gelderlandplein.dummy

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gelderlandplein.models.Shop

class ShopRepository(context: Context) {
    private var shopDao: ShopDao?

    init {
        val shopRoomDatabase = ShopRoomDatabase.getDatabase(context)
        shopDao = shopRoomDatabase?.shopDao()
    }

    fun getAllShops() : LiveData<List<Shop>> {
        return shopDao?.getAllShops() ?: MutableLiveData(emptyList())
    }

    suspend fun insertShop(shop: Shop) {
        shopDao?.insertShop(shop)
    }


    suspend fun deleteShop(shop: Shop) {
        shopDao?.deleteShop(shop)
    }

    suspend fun updateShop(shop: Shop) {
        shopDao?.updateShop(shop)
    }

}
