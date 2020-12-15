package com.example.gelderlandplein.dummy

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gelderlandplein.models.Shop

@Dao
interface ShopDao {
    @Query("SELECT * FROM shopTable")
    fun getAllShops(): LiveData<List<Shop>>

    @Insert
    fun insertShop(shop: Shop)

    @Delete
    suspend fun deleteShop(shop: Shop)

    @Update
    suspend fun updateShop(shop: Shop)

}
