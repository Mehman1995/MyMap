package ru.netology.travel_in_russia_maps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.travel_in_russia_maps.R
import ru.netology.travel_in_russia_maps.adapter.PlaceCallback
import ru.netology.travel_in_russia_maps.adapter.PlacesAdapter
import ru.netology.travel_in_russia_maps.databinding.FragmentListOfPlacesBinding
import ru.netology.travel_in_russia_maps.dto.Place
import ru.netology.travel_in_russia_maps.viewModel.PlaceViewModel


class ListOfPlacesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListOfPlacesBinding.inflate(inflater, container, false)

        val bundle = Bundle()

        val viewModel: PlaceViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )


        val adapter = PlacesAdapter(object : PlaceCallback {
            override fun remove(place: Place) {
                viewModel.removeById(place.id)
            }

            override fun edit(place: Place) {
                viewModel.edit(place)
                bundle.putString("name", place.name)
                bundle.putString("description", place.description)
                place.latitude?.let { bundle.putDouble("latitude", it) }
                place.longitude?.let { bundle.putDouble("longitude", it) }
                findNavController().navigate(
                    R.id.action_listOfPointsFragment_to_newPlaceFragment,
                    bundle
                )
            }

            override fun onPlace(place: Place) {
                val id = place.id
                bundle.putLong("id", id)

                findNavController().navigate(
                    R.id.action_listOfPointsFragment_to_mapsFragment,
                    bundle
                )
            }

            override fun onVisited(place: Place) {
                if (!place.visited) viewModel.visited(place) else viewModel.notVisited(place)
            }

        })

        binding.list.adapter = adapter

        viewModel.edit(Place.empty)
        viewModel.data.observe(viewLifecycleOwner) { state ->
            val listComparison = adapter.itemCount < state.places.size
            adapter.submitList(state.places) {
                if (listComparison) binding.list.scrollToPosition(0)
            }

            binding.emptyText.isVisible = state.empty
        }

        binding.newPoint.setOnClickListener {
            findNavController().navigate(R.id.action_listOfPointsFragment_to_newPlaceFragment)

        }

        return binding.root
    }
}