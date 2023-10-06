package com.agalobr.androidtrainning.app

sealed class ErrorApp {
    object UnknowError : ErrorApp()
}