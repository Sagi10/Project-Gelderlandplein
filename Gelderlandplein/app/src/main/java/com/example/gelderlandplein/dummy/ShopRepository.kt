
package com.example.gelderlandplein.dummy

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gelderlandplein.models.Shop
import com.example.gelderlandplein.models.ShopList

class ShopRepository(context: Context) {
    private var shopDao: ShopDao?

    init {
        val shopRoomDatabase = ShopRoomDatabase.getDatabase(context)
        shopDao = shopRoomDatabase?.shopDao()
    }

    fun getAllShops() : LiveData<ShopList> {
        return shopDao?.getAllShops() ?: MutableLiveData()
    }

    suspend fun insertShop(shop: ShopList) {
        shopDao?.insertShop(shop)
    }


    suspend fun deleteShop(shop: ShopList) {
        shopDao?.deleteShop(shop)
    }

    suspend fun updateShop(shop: ShopList) {
        shopDao?.updateShop(shop)
    }

}

