package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.viewmodel.AppState
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveDate().observe(viewLifecycleOwner, Observer<AppState> {
            renderData(it)
        })
        viewModel.getADtaFromRemoteSource()
    }

    fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> TODO()
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
                binding.message.text = "Failure"
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.message.text = "Success"
                Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}