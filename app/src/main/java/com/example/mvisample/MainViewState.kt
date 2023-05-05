package com.example.mvisample

sealed class MainViewState {

    object Idle : MainViewState()

    // number
    data class Number(val number: Int) : MainViewState()

    //Error
    data class Error(val ErrorMessage: String) : MainViewState()

}
