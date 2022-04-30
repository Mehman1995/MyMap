package ru.netology.travel_in_russia_maps.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.netology.travel_in_russia_maps.dao.PlaceDao
import ru.netology.travel_in_russia_maps.entity.DraftEntity
import ru.netology.travel_in_russia_maps.entity.PlaceEntity

@Database(entities = [PlaceEntity::class, DraftEntity::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun placeDao(): PlaceDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(context, AppDb::class.java, "app.db")
//            .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }
}
