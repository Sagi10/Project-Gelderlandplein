
package com.example.gelderlandplein.dummy
/*
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gelderlandplein.models.Shop
import com.example.gelderlandplein.models.ShopList

@Database(entities = [ShopList::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ShopRoomDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao

    companion object {
        private const val DATABASE_NAME = "SHOP_DATABASE"

        @Volatile
        private var shopRoomDatabase: ShopRoomDatabase? = null

        fun getDatabase(context: Context): ShopRoomDatabase? {
            if (shopRoomDatabase == null) {
                synchronized(ShopRoomDatabase::class.java) {
                    if (shopRoomDatabase == null) {
                        shopRoomDatabase = Room.databaseBuilder(
                            context.applicationContext,
                            ShopRoomDatabase::class.java, DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return shopRoomDatabase
        }
    }
}
*/
