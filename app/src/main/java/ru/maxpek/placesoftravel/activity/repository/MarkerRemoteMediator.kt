package ru.maxpek.placesoftravel.activity.repository

import android.os.IBinder
import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.google.android.gms.dynamic.RemoteCreator
import ru.maxpek.placesoftravel.activity.db.AppDb
import ru.maxpek.placesoftravel.activity.marker.Marker
import javax.inject.Inject

//@OptIn(ExperimentalPagingApi::class)
//class MarkerRemoteMediator@Inject constructor(
//    private val db: AppDb) : RemoteCreator<Marker>() {
//    override fun getRemoteCreator(p0: IBinder?): Marker {
//        TODO("Not yet implemented")
//    }
//
//}