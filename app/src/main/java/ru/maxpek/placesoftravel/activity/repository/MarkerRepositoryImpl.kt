package ru.maxpek.placesoftravel.activity.repository

import android.renderscript.Long2
import androidx.lifecycle.LiveData

import androidx.paging.ExperimentalPagingApi

import ru.maxpek.placesoftravel.activity.dao.MarkerDao
import ru.maxpek.placesoftravel.activity.db.AppDb
import ru.maxpek.placesoftravel.activity.db.DbModule_ProvideAppDbFactory.provideAppDb
import ru.maxpek.placesoftravel.activity.marker.Marker
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MarkerRepositoryImpl @Inject constructor(
    private val dao: MarkerDao,
    private val db: AppDb
):MarkerRepository {
    private val list = listOf<Marker>()

    override val dataMarkers: Unit =  db.markerDao().insert(list)


        private val markers = dao.getAll().value
        override fun getAll(): LiveData<List<Marker>> {
            return dao.getAll()
        }

        override fun removeById(id: Long) {
            dao.removeById(id)
        }

        override fun addMarker(marker: Marker) : Long{
            return dao.insert(marker)
        }

        override fun outputMarker(id: Long): Marker {
            return markers!!.first { it.id == id }
        }

    override fun isSize(): Long {
        val size = dao.isSize()
        return if (size == 0){
            0L
        } else size.toLong()
    }

}
