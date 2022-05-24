package ru.maxpek.placesoftravel.activity.marker

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yandex.mapkit.geometry.Point

@Entity
data class Marker (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val pointLatitude : Double,
    val pointLongitude : Double
)