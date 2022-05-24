package ru.maxpek.placesoftravel.activity.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.maxpek.placesoftravel.activity.dao.MarkerDao
import ru.maxpek.placesoftravel.activity.marker.Marker

@Database(entities = [Marker::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun markerDao(): MarkerDao
}

//@Database(entities = [Marker::class], version = 1)
//abstract class AppDb : RoomDatabase() {
//    abstract val markerDao: MarkerDao
//
//    companion object {
//        @Volatile
//        private var instance: AppDb? = null
//
//        fun getInstance(context: Context): AppDb{
//            return instance ?: synchronized(this){
//                instance ?: buildDatabase(context).also { instance = it}
//            }
//        }
//
//        private fun buildDatabase(context: Context)
//                = Room.databaseBuilder(context, AppDb::class.java, "app.db")
//            .allowMainThreadQueries()
//            .build()
//    }
//}