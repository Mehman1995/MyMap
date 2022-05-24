package ru.maxpek.placesoftravel.activity.repository

import androidx.lifecycle.LiveData
import ru.maxpek.placesoftravel.activity.marker.Marker

interface MarkerRepository {
    val dataMarkers: Unit
    fun getAll(): LiveData<List<Marker>>
    fun removeById(id: Long)
    fun addMarker(marker: Marker): Long
    fun outputMarker(id: Long): Marker
    fun isSize(): Long
}