package ru.maxpek.placesoftravel.activity.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.maxpek.placesoftravel.R
import ru.maxpek.placesoftravel.activity.marker.Marker
import ru.maxpek.placesoftravel.databinding.CardMarkerBinding

interface AdapterCallback {
    fun onEdit(marker: Marker) {}
    fun onRemove(id: Long) {}
    fun outputToTheScreen (id: Long) {}
}

class MarkerAdapter (private val callback: AdapterCallback) :
    ListAdapter<Marker, MarkerViewHolder>(MarkerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {
        val binding = CardMarkerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarkerViewHolder (binding, callback)
    }

    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}

class MarkerViewHolder
    (private val binding: CardMarkerBinding,
     private val callback: AdapterCallback)  : RecyclerView.ViewHolder(binding.root) {

    fun bind(marker: Marker) {
        binding.apply {
            title.text = marker.title
            pointLatitude.text = marker.pointLatitude.toString()
            pointLongitude.text = marker.pointLongitude.toString()

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_markers)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> callback.onRemove(marker.id)
                            R.id.edit -> callback.onEdit(marker)
                            R.id.output_to_the_screen -> callback.outputToTheScreen(marker.id)
                        }
                        true
                    }
                }.show()
            }
        }
    }
}

class MarkerDiffCallback : DiffUtil.ItemCallback<Marker>() {
    override fun areItemsTheSame(oldItem: Marker, newItem: Marker): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Marker, newItem: Marker): Boolean {
        return oldItem == newItem
    }
}