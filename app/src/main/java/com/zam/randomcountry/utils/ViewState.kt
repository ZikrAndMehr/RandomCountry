package com.zam.randomcountry.utils

sealed class ViewState<T>(
    val value: T? = null,
    val error: String? = null,
    val throwable: Throwable? = null
) {
    class Idle<T> : ViewState<T>()

    class Loading<T> : ViewState<T>()

    class Success<T>(data: T) : ViewState<T>(data)

    class Error<T>(error: String? = null, throwable: Throwable? = null) :
        ViewState<T>(error = error, throwable = throwable)
}
