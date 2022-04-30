package ru.netology.travel_in_russia_maps.dto

data class Place(
    val id: Long,
    val visited: Boolean,
    val name: String,
    val description: String,
    val latitude: Double?,
    val longitude: Double?
) {
    companion object {
        val empty = Place(
            id = 0,
            visited = false,
            name = "",
            description = "",
            latitude = null,
            longitude = null
        )
    }
}