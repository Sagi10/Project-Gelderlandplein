/*
package com.example.gelderlandplein.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gelderlandplein.dummy.ShopRepository
import com.example.gelderlandplein.models.Shop
import com.example.gelderlandplein.models.ShopList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val shopRepository = ShopRepository(application.applicationContext)

    val shops: LiveData<ShopList> = shopRepository.getAllShops()

    fun insertShop(shop: ShopList) {
        ioScope.launch {
            shopRepository.insertShop(shop)
        }
    }

    fun deleteShop(shop: ShopList) {
        ioScope.launch {
            shopRepository.deleteShop(shop)
        }
    }
}
*/
