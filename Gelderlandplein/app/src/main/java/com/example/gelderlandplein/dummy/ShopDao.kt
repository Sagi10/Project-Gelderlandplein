
package com.example.gelderlandplein.dummy

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gelderlandplein.models.Shop
import com.example.gelderlandplein.models.ShopList

@Dao
interface ShopDao {
    @Query("SELECT * FROM shopTable")
    fun getAllShops(): LiveData<ShopList>

    @Insert
    fun insertShop(shop: ShopList)

    @Delete
    suspend fun deleteShop(shop: ShopList)

    @Update
    suspend fun updateShop(shop: ShopList)

}

