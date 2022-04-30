package ru.netology.travel_in_russia_maps.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.ktx.awaitAnimateCamera
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ktx.utils.collection.addMarker
import kotlinx.coroutines.launch
import ru.netology.travel_in_russia_maps.R
import ru.netology.travel_in_russia_maps.databinding.FragmentMapBinding
import ru.netology.travel_in_russia_maps.dto.Place
import ru.netology.travel_in_russia_maps.utils.icon
import ru.netology.travel_in_russia_maps.viewModel.PlaceViewModel

class MapFragment : Fragment() {

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val bundle = Bundle()

    private val viewModel: PlaceViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                googleMap.apply {
                    isMyLocationEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true
                }

                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity())

                val id = arguments?.getLong("id")
                var showPlace: Place? = null

                viewModel.data.observe(viewLifecycleOwner) { feedModel ->
                    feedModel.places.map { place ->
                        if (place.id == id) {
                            showPlace = place
                        }
                    }
                }

                if (showPlace?.id == null) {
                    fusedLocationClient.lastLocation.addOnSuccessListener {
                        lifecycle.coroutineScope.launchWhenCreated {
                            googleMap.awaitAnimateCamera(
                                CameraUpdateFactory.newCameraPosition(
                                    cameraPosition {
                                        target(LatLng(it.latitude, it.longitude))
                                        zoom(30F)
                                    }
                                ))
                        }
                    }
                } else {
                    lifecycle.coroutineScope.launch {
                        googleMap.awaitAnimateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                cameraPosition {
                                    showPlace!!.latitude?.let {
                                        showPlace!!.longitude?.let { it1 ->
                                            LatLng(
                                                it,
                                                it1
                                            )
                                        }
                                    }?.let {
                                        target(
                                            it
                                        )
                                    }
                                    zoom(30F)
                                }
                            ))
                    }
                }
            } else {
                // TODO: show sorry dialog
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMapBinding.inflate(inflater, container, false)

        binding.list.setOnClickListener {
            findNavController().navigate(R.id.action_mapsFragment_to_listOfPointsFragment)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment



        lifecycle.coroutineScope.launchWhenCreated {
            googleMap = mapFragment.awaitMap().apply {
                isTrafficEnabled = true
                isBuildingsEnabled = true

                uiSettings.apply {
                    isZoomControlsEnabled = true
                    setAllGesturesEnabled(true)
                }
            }

            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)



            googleMap.setOnMapLongClickListener { point ->
                val latitude = point.latitude
                val longitude = point.longitude

                bundle.putDouble("latitude", latitude)
                bundle.putDouble("longitude", longitude)

                findNavController().navigate(R.id.action_mapsFragment_to_newPlaceFragment, bundle)

            }

            viewModel.data.observe(viewLifecycleOwner) { feedModel ->
                val markerManager = MarkerManager(googleMap)
                markerManager.newCollection().apply {
                    feedModel.places.forEach { place ->
                        addMarker {
                            place.latitude?.let { place.longitude?.let { it1 -> LatLng(it, it1) } }
                                ?.let { position(it) }
                            icon(
                                getDrawable(
                                    requireContext(),
                                    R.drawable.ic_baseline_location_on_24
                                )!!
                            )
                            title(place.name)
                        }
                    }
                }

            }

        }
    }
}
