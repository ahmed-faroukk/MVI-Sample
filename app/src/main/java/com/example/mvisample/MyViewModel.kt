package com.example.mvisample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch


class MyViewModel : ViewModel() {
    //data ---> from viewModel to Activity
    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: MutableStateFlow<MainViewState> get() = _viewState

    //data ---> Activity to ViewModel
    val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)

    //login
    private var number = 0

    init {
        processIntent()
    }

    //process
    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.AddNumber -> addNumber()
                    is MainIntent.MinusNumber -> minusNumber()
                }
            }
        }
    }

    //reduce
    private fun addNumber() {
        viewModelScope.launch {
            _viewState.value =
                try {
                    number += 1
                    MainViewState.Number(number)

                } catch (t: Throwable) {
                    MainViewState.Error(t.message.toString())
                }
        }
    }

    private fun minusNumber(){
        viewModelScope.launch {
            _viewState.value =
            try {
                number -=1
                MainViewState.Number(number)
            }catch (t :Throwable){
                MainViewState.Error(t.message.toString())
            }
        }
    }

}