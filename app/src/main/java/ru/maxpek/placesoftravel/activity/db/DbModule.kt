package ru.maxpek.placesoftravel.activity.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.maxpek.placesoftravel.activity.dao.MarkerDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    @Singleton
    @Provides
    fun provideAppDb(
        @ApplicationContext context: Context
    ): AppDb = Room.databaseBuilder(context, AppDb::class.java, "app.db")
        .allowMainThreadQueries()
        .build()

//    var db: AppDatabase = Room.databaseBuilder(
//        getApplicationContext(),
//        AppDatabase::class.java, "database-name"
//    ).build()

    @Provides
    fun providePostDao(appDb: AppDb): MarkerDao = appDb.markerDao()
}

