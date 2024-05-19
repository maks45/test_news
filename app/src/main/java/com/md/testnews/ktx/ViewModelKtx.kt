package com.md.testnews.ktx

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler


fun ViewModel.exceptionHandler(action: (Throwable) -> Unit): CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
        Log.e("coroutine exception handler:", throwable.message ?: "")
        action.invoke(throwable)
    }

val defaultErrorMessage: String
    get() = "Something went wrong"