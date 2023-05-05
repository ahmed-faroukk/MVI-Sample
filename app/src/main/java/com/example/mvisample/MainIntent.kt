package com.example.mvisample

sealed class MainIntent {
    object AddNumber : MainIntent()
    object MinusNumber : MainIntent()

}