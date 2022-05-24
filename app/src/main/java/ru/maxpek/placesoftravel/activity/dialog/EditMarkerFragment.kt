package ru.maxpek.placesoftravel.activity.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.maxpek.placesoftravel.activity.dialog.NewMarkerFragment.Companion.textArg
import ru.maxpek.placesoftravel.databinding.FragmentEditMarkerBinding
import ru.maxpek.placesoftravel.viewmodel.MarkerViewModel

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class EditMarkerFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditMarkerBinding.inflate(inflater, container, false)
        val viewModel: MarkerViewModel by viewModels(ownerProducer = ::requireParentFragment)

        arguments?.textArg?.let { binding.textMarker.setText(it) }

        binding.cancel.setOnClickListener { findNavController().navigateUp() }
        binding.edit.setOnClickListener{
            val text = binding.textMarker.text.toString()
            if (text.isNotBlank()) {
                viewModel.changeContent(text)
                viewModel.addMarker()
            }
            findNavController().navigateUp()
        }

        return binding.root
    }
}