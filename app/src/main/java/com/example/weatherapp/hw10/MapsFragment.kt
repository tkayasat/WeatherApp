package com.example.weatherapp.hw10

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*


class MapsFragment : Fragment() {

    private lateinit var map: GoogleMap

    companion object {
        fun newInstance() = MapFragment()
    }

    private val markers: ArrayList<Marker> = arrayListOf()

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        val startPlace = LatLng(55.0, 37.0)
        map.addMarker(MarkerOptions().position(startPlace).title("Marker Start"))
        map.moveCamera(CameraUpdateFactory.newLatLng(startPlace))
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isZoomGesturesEnabled = true

        val isPermissionGranted =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
                    PackageManager.PERMISSION_GRANTED

        map.setMyLocationEnabled(isPermissionGranted)
        map.uiSettings.isMyLocationButtonEnabled = true

        map.setOnMapLongClickListener { location ->
            moveToPosition(location)
            addMarker(location)
            drawLine()
        }
    }

    private fun addMarker(location: LatLng) {
        markers.add(
            map.addMarker(
                MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin))
                    .position(location)
                    .title("")
            )!!
        )
    }

    private fun drawLine() {
        val last = markers.size - 1
        if (last > 0) {
            val startMarker = markers[last - 1].position
            val endMarker = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(startMarker, endMarker)
                    .color(Color.RED)
                    .width(5f)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentGoogleMapsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        binding.buttonSearch.setOnClickListener {
            val geocoder = Geocoder(requireContext())
            val addressRow = binding.searchAddress.text.toString()
            val address = geocoder.getFromLocationName(addressRow, 1)
            val location = LatLng(address[0].latitude, address[0].longitude)
            moveToPosition(location)
        }
    }

    private fun moveToPosition(location: LatLng) {

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }
}