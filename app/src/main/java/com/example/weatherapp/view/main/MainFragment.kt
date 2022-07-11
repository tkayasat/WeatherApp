package com.example.weatherapp.view.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.view.OnItemViewClickListener
import com.example.weatherapp.view.details.DetailsFragment
import com.example.weatherapp.viewmodel.MainState
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), OnItemViewClickListener, TextView.OnEditorActionListener {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private var isDataSetRussian: Boolean = true
    private val adapter = MainFragmentAdapter()

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var weatherData: List<Weather> = ArrayList()

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            adapter.setOnItemViewClickListener(this@MainFragment)
            mainFragmentFAB.setOnClickListener {
                isDataSetRussian = !isDataSetRussian
                if (isDataSetRussian) {
                    viewModel.getWeatherFromLocalSourceRussian()
                    mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                } else {
                    viewModel.getWeatherFromLocalSourceWorld()
                    mainFragmentFAB.setImageResource(R.drawable.ic_earth)
                }
            }
            etCityFilter.setOnEditorActionListener(this@MainFragment)
        }

        viewModel.getLiveDate()
            .observe(viewLifecycleOwner, Observer<MainState> { mainState: MainState ->
                renderData(mainState)
            })
        viewModel.getWeatherFromLocalSourceRussian()
    }

    private fun renderData(mainState: MainState) {
        when (mainState) {
            is MainState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                val throwable = mainState.error
                Snackbar.make(binding.root, "ERROR $throwable", Snackbar.LENGTH_LONG).show()
            }
            MainState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is MainState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                weatherData = mainState.weatherData
                adapter.setWeather(filterWeatherData())
                binding.root.showSnackbarWithoutAction(
                    binding.root,
                    R.string.success,
                    Snackbar.LENGTH_LONG
                )
                binding.root.showSnackbarWithAction(
                    binding.root, R.string.success, Snackbar.LENGTH_LONG, R.string.action_success
                ) {
                    if (isDataSetRussian) {
                        viewModel.getWeatherFromLocalSourceRussian()
                    } else {
                        viewModel.getWeatherFromLocalSourceWorld()
                    }
                }
            }
        }
    }

    private fun View.showSnackbarWithoutAction(view: View, stringId: Int, length: Int) {
        Snackbar.make(binding.root, getString(stringId), length).show()
    }

    private fun View.showSnackbarWithAction(
        view: View, stringResultText: Int, length: Int, stringActionText: Int,
        listener: View.OnClickListener,
    ) {
        Snackbar.make(view, getString(stringResultText), length)
            .setAction(getString(stringResultText), listener).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(weather: Weather) {
        val bundle = Bundle()
        bundle.putParcelable(DetailsFragment.BUNDLE_WEATHER_KEY, weather)
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, DetailsFragment.newInstance(bundle))
            .addToBackStack("")
            .commit()
    }

    override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
        if (p1 == 5) {
            adapter.setWeather(filterWeatherData())
        }

        return true
    }

    private fun filterWeatherData(): List<Weather> {
        if (binding.etCityFilter.text.toString() == "")
            return weatherData

        val filteredWeatherData: MutableList<Weather> = ArrayList()

        weatherData.forEach {
            if (it.city.toString().contains(binding.etCityFilter.text.toString(), true))
                filteredWeatherData.add(it)
        }
        return filteredWeatherData
    }
}