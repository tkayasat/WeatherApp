package com.example.weatherapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentDetailsBinding
import com.example.weatherapp.domain.Weather

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    companion object {
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }

        const val BUNDLE_WEATHER_KEY = "key"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val weather = (it?.getParcelable<Weather>(BUNDLE_WEATHER_KEY)) ?: Weather()
            setData(weather)
        }
    }

    private fun setData(weather: Weather) {
        with(binding) {
            with(weather) {
                cityName.text = city.name
                cityCoordinates.text = "lat ${city.lat}\n lon ${city.lon}"
                temperatureValue.text = temperature.toString()
                feelsLikeValue.text = feelsLike.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}