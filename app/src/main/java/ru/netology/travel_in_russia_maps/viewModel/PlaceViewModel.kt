package ru.netology.travel_in_russia_maps.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.travel_in_russia_maps.db.AppDb
import ru.netology.travel_in_russia_maps.dto.Place
import ru.netology.travel_in_russia_maps.model.FeedModel
import ru.netology.travel_in_russia_maps.model.FeedModelState
import ru.netology.travel_in_russia_maps.repository.PlaceRepository
import ru.netology.travel_in_russia_maps.repository.PlaceRepositoryImpl
import ru.netology.travel_in_russia_maps.utils.SingleLiveEvent

private val emptyPlace = Place(
    id = 0,
    visited = false,
    name = "",
    description = "",
    latitude = 0.0,
    longitude = 0.0
)

class PlaceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlaceRepository =
        PlaceRepositoryImpl(
            AppDb.getInstance(context = application).placeDao()
        )

    val data: LiveData<FeedModel> = repository.data
        .map { places ->
            FeedModel(
                places,
                places.isEmpty()
            )
        }.asLiveData(Dispatchers.Default)


    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    private val edited = MutableLiveData(emptyPlace)
    private val _placeCreated = SingleLiveEvent<Unit>()
    val placeCreated: LiveData<Unit>
        get() = _placeCreated

    init {
        loadPlaces()
    }

    private fun loadPlaces() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(loading = true)
            repository.getAll()
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }


    fun save() {
        edited.value?.let {
            _placeCreated.value = Unit
            viewModelScope.launch {
                try {
                    repository.save(it)
                    repository.saveDraft(null, null)
                    _dataState.value = FeedModelState()
                } catch (e: Exception) {
                    _dataState.value = FeedModelState(error = true)
                }
            }
        }

    }

    fun changeContent(name: String, description: String, local: String) {

        val coordinates = local.split(";")
        val latitude = coordinates[0].toDouble()
        val longitude = coordinates[1].toDouble()
        edited.value?.let {
            val textName = name.trim()
            val textDescription = description.trim()
            if (it.name != textName ||
                it.description != textDescription ||
                it.latitude != latitude ||
                longitude != longitude
            ) {
                edited.value = it.copy(
                    name = textName,
                    description = textDescription,
                    latitude = latitude,
                    longitude = longitude
                )
            }
        }
    }

    fun edit(place: Place?) {
        edited.value = place
    }

    fun visited(place: Place) = viewModelScope.launch {
        try {
            repository.visited(place.id)
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value =
                FeedModelState(error = true)
        }
    }

    fun notVisited(place: Place) = viewModelScope.launch {
        try {
            repository.notVisited(place.id)
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value =
                FeedModelState(error = true)
        }
    }

    fun removeById(id: Long) = viewModelScope.launch {
        try {
            repository.removeById(id)
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value =
                FeedModelState(error = true)
        }
    }

    fun saveDraft(name: String?, description: String) =
        viewModelScope.launch { repository.saveDraft(name, description) }


    suspend fun getDraftName(): String? = repository.getDraftName()

    suspend fun getDraftDescription(): String? = repository.getDraftDescription()

}