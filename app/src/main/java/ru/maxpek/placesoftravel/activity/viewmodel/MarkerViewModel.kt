package ru.maxpek.placesoftravel.viewmodel

import androidx.lifecycle.*
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.maxpek.placesoftravel.activity.marker.Marker
import ru.maxpek.placesoftravel.activity.repository.MarkerRepository
import javax.inject.Inject

private val empty = Marker(
    id = 0,
    title = "",
    pointLatitude = 59.945933,
    pointLongitude = 30.320045
)
@ExperimentalCoroutinesApi
@HiltViewModel
class MarkerViewModel @Inject constructor(
    private val repository: MarkerRepository): ViewModel() {

    private val edited = MutableLiveData(empty)
    private var nextId: Long = repository.isSize()
    var point: Point = Point(0.0,0.0)
    val data = repository.getAll()

    fun removeById(id: Long){
        repository.removeById(id)
    }

    fun addMarker(){
        edited.value?.let {
            nextId = repository.addMarker(it)
        }
        edited.value = empty

    }

    fun edit(marker: Marker){
        edited.value = marker
    }
    fun outputMarker(id: Long): Marker {
        val marker =  data.value?.first { it.id == id }
        return marker!!
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.title == text) {
            return
        }

        if (edited.value?.id!! == 0L){
            edited.value = edited.value?.copy(id = nextId+1L, title = text,
                pointLatitude = point.latitude,
                pointLongitude = point.longitude)
        } else {
            edited.value = edited.value?.copy(title = text)
        }
    }



}