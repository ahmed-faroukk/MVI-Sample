package com.example.mvisample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.mvisample.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = MyViewModel()
        send()
        render()

    }

    private fun render() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainViewState.Idle -> "Idle"
                    is MainViewState.Number -> binding.textView.text = it.number.toString()
                    is MainViewState.Error -> binding.textView.text = it.ErrorMessage
                }
            }
        }
    }

    private fun send() {
        binding.btn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intentChannel.send(MainIntent.AddNumber)
            }
        }
        binding.btn1.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intentChannel.send(MainIntent.MinusNumber)

            }
        }
    }
}