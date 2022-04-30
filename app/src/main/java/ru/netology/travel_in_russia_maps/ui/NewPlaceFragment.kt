package ru.netology.travel_in_russia_maps.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.travel_in_russia_maps.R
import ru.netology.travel_in_russia_maps.databinding.FragmentNewPlaceBinding


import ru.netology.travel_in_russia_maps.viewModel.PlaceViewModel

class NewPlaceFragment : Fragment() {

   private val viewModel: PlaceViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewPlaceBinding.inflate(inflater, container, false)

        val description = arguments?.getString("description")
        val name = arguments?.getString("name")
        val latitude = arguments?.getDouble("latitude")
        val longitude = arguments?.getDouble("longitude")
        val local =
            if (latitude == null || longitude == null) {
                ""
            } else {
                "$latitude;$longitude"
            }


        binding.nameFieldEdit.setText(name)
        binding.descriptionFieldEdit.setText(description)
        binding.locationCoordinatesEdit.setText(local)

        lifecycleScope.launchWhenCreated {
            viewModel.getDraftName()?.let(binding.nameFieldEdit::setText)
            viewModel.getDraftDescription()?.let(binding.descriptionFieldEdit::setText)
        }

        binding.nameFieldEdit.requestFocus()

        binding.selectMap.setOnClickListener {
            viewModel.saveDraft(
                binding.nameFieldEdit.text.toString(),
                binding.descriptionFieldEdit.text.toString()
            )
            findNavController().navigate(R.id.action_newPlaceFragment_to_mapsFragment)
        }


        binding.save.setOnClickListener {

            val nameNewPlace = binding.nameFieldEdit.text.toString()
            val localNewPlace = binding.locationCoordinatesEdit.text.toString()
            val descriptionNewPlace = binding.descriptionFieldEdit.text.toString()

            when {
                (nameNewPlace.isEmpty()) -> {
                    Snackbar.make(
                        binding.root,
                        R.string.error_empty_name,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                (localNewPlace.isEmpty()) -> {
                    Snackbar.make(
                        binding.root,
                        R.string.error_empty_coordinates,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                else -> {
                    viewModel.changeContent(nameNewPlace, descriptionNewPlace, localNewPlace)
                    viewModel.save()
                    findNavController().navigate(R.id.action_newPlaceFragment_to_listOfPointsFragment)
                }
            }
        }

        return binding.root
    }

}


