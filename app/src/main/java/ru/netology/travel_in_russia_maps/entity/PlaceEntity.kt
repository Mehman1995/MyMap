package ru.netology.travel_in_russia_maps.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.travel_in_russia_maps.dto.Place

@Entity
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val visited: Boolean,
    val name: String,
    val description: String,
    val latitude: Double?,
    val longitude: Double?

) {
    fun toDto() =
        Place(
            id, visited, name, description, latitude, longitude
        )

    companion object {
        fun fromDto(place: Place) =
            PlaceEntity(
                place.id,
                place.visited,
                place.name,
                place.description,
                place.latitude,
                place.longitude,
            )
    }

}

fun List<PlaceEntity>.toDto() = map(PlaceEntity::toDto)


@Entity
data class DraftEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String?,
    val description: String?
)