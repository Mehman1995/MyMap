package ru.netology.travel_in_russia_maps.model

import ru.netology.travel_in_russia_maps.dto.Place

data class FeedModel(
    val places: List<Place> = emptyList(),
    val empty: Boolean = false
)

data class FeedModelState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val refreshing: Boolean = false,
)