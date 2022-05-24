package ru.maxpek.placesoftravel.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.maxpek.placesoftravel.R
import ru.maxpek.placesoftravel.activity.adapter.AdapterCallback
import ru.maxpek.placesoftravel.activity.adapter.MarkerAdapter
import ru.maxpek.placesoftravel.activity.dialog.NewMarkerFragment.Companion.pointArg
import ru.maxpek.placesoftravel.activity.dialog.NewMarkerFragment.Companion.textArg
import ru.maxpek.placesoftravel.activity.marker.Marker
import ru.maxpek.placesoftravel.viewmodel.MarkerViewModel
import ru.maxpek.placesoftravel.databinding.FragmentListOfMarkersBinding

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ListOfMarkersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListOfMarkersBinding.inflate(inflater, container, false)

        val viewModel: MarkerViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = MarkerAdapter (object : AdapterCallback {
            override fun onEdit(marker: Marker) {
                viewModel.edit(marker)
                findNavController().navigate(
                    R.id.action_listOfMarkersFragment_to_editMarkerFragment,
                    Bundle().apply { textArg = marker.title })
            }
            override fun onRemove(id: Long) {
                viewModel.removeById(id)
            }
            override fun outputToTheScreen (id: Long) {
                val marker = viewModel.outputMarker(id)
                findNavController().navigate(
                    R.id.action_listOfMarkersFragment_to_mapsFragment,
                    Bundle().apply { pointArg = Point(marker.pointLatitude, marker.pointLongitude) })
            }
        })

        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) {
            val newMarker = adapter.itemCount < it.size
            adapter.submitList(it) {
                if (newMarker) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }
        return binding.root
    }
}