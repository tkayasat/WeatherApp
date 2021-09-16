package com.example.weatherapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.view.OnItemViewClickListener
import com.example.weatherapp.viewmodel.AppState
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), OnItemViewClickListener {

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

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        }

        viewModel.getLiveDate()
            .observe(viewLifecycleOwner, Observer<AppState> { appState: AppState ->
                renderData(appState)
            })
        viewModel.getWeatherFromLocalSourceRussian()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                val throwable = appState.error
                Snackbar.make(binding.root, "ERROR $throwable", Snackbar.LENGTH_LONG).show()
            }
            AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                val weather = appState.weatherDate
                adapter.setWeather(weather)
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
        listener: View.OnClickListener
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
}