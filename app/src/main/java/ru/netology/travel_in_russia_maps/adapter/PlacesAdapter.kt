package ru.netology.travel_in_russia_maps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.travel_in_russia_maps.R
import ru.netology.travel_in_russia_maps.databinding.CardMarkedPlacesBinding
import ru.netology.travel_in_russia_maps.dto.Place
import ru.netology.travel_in_russia_maps.utils.addAllOnClickListener

interface PlaceCallback {
    fun remove(place: Place)
    fun edit(place: Place)
    fun onPlace(place: Place)
    fun onVisited(place: Place)
}

class PlacesAdapter(private val placeCallback: PlaceCallback) :
    ListAdapter<Place, PlaceViewHolder>(PlacesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding =
            CardMarkedPlacesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding, placeCallback)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
    }

}

class PlaceViewHolder(
    private val binding: CardMarkedPlacesBinding,
    private val placeCallback: PlaceCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(place: Place) {

        with(binding) {
            namePlace.text = place.name
            description.text = place.description
            checkBox.isChecked = place.visited

            checkBox.setOnClickListener {
                placeCallback.onVisited(place)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.place_options)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.place_remove -> {
                                placeCallback.remove(place)
                                true
                            }
                            R.id.place_edit -> {
                                placeCallback.edit(place)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

            group.addAllOnClickListener { placeCallback.onPlace(place) }

        }

    }
}

class PlacesDiffCallback : DiffUtil.ItemCallback<Place>() {

    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }

}
