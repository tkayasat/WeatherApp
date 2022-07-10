package com.example.weatherapp.hw6

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentThreadsBinding
import java.util.*

class ThreadsFragment : Fragment() {

    companion object {
        fun newInstance() = ThreadsFragment()
    }

    private var _binding: FragmentThreadsBinding? = null
    private val binding: FragmentThreadsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentThreadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // DANGER
        binding.button.setOnClickListener {
            val timer = binding.editText.text.toString().toInt()
            startCalculations(timer)
        }
        val myHandler1991 = Handler(Looper.myLooper()!!)
        binding.calcThreadBtn.setOnClickListener {

            Thread {
                val myHandler1993 = Handler(Looper.getMainLooper())
                val timer = binding.editText.text.toString().toInt()
                startCalculations(timer)
                myHandler1991.post(Runnable {
                    binding.mainContainer.addView(TextView(it.context).apply {
                        text = "Thread myHandler1991"
                        textSize = 30f
                    })
                })
                myHandler1993.post(Runnable {
                    binding.mainContainer.addView(TextView(it.context).apply {
                        text = "Thread myHandler1993"
                        textSize = 30f
                    })
                })

            }.start()
        }

        val handlerThread = MyThread()
        handlerThread.start()

        binding.calcThreadHandlerBtn.setOnClickListener {
            val handler = handlerThread.mHandler
            handler?.post {
                val timer = binding.editText.text.toString().toInt()
                startCalculations(timer)
                myHandler1991.post(Runnable {
                    binding.mainContainer.addView(TextView(it.context).apply {
                        text = "HandlerThread myHandler1991"
                        textSize = 30f
                    })
                })
            }
        }
        handlerThread.mHandler?.looper?.quitSafely()
        handlerThread.mHandler?.looper?.quit()
    }

    private fun startCalculations(timer: Int) {
        val currentTime = Date().time
        while ((currentTime + timer * 1000) > Date().time) {
        }
    }
}

class MyThread : Thread() {
    var mHandler: Handler? = null
    override fun run() {
        Looper.prepare()
        mHandler = Handler(Looper.myLooper()!!)
        Looper.loop()
    }
}