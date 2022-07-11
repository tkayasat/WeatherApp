package com.example.weatherapp.hw6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherapp.databinding.FragmentThreadsBinding
import java.util.*

const val TEST_BROADCAST_INTENT_FILTER = "TEST BROADCAST INTENT FILTER"
const val THREADS_FRAGMENT_BROADCAST_EXTRA = "THREADS_FRAGMENT_EXTRA"

class ThreadsFragment : Fragment() {

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            binding.mainContainer.addView(TextView(context).apply {
                val message = intent?.getStringExtra(THREADS_FRAGMENT_BROADCAST_EXTRA)
                text = "S+BR $message"
                textSize = 30f
            })
        }
    }

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
        firstPart()
        registerBroadcast()
    }

    private fun registerBroadcast() {
        LocalBroadcastManager.getInstance(requireActivity())
            .registerReceiver(receiver, IntentFilter(TEST_BROADCAST_INTENT_FILTER))
    }

    private fun firstPart() {
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
        handlerThread.mHandler?.looper?.quitSafely() // ожидаем завершения всех задач
        handlerThread.mHandler?.looper?.quit()// выходим незамедлительно


        binding.btnService.setOnClickListener {
            context?.let {
                val intent = Intent(it, MainService::class.java)
                intent.putExtra(MAIN_SERVICE_STRING_EXTRA, "привет сервис, я фрагмент")
                it.startService(intent)
            }
        }
        binding.btnServiceBroadcast.setOnClickListener {
            context?.let {
                val intent = Intent(it, MainService::class.java)
                intent.putExtra(MAIN_SERVICE_STRING_EXTRA, "привет сервис, я фрагмент")
                it.startService(intent)
            }
        }
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