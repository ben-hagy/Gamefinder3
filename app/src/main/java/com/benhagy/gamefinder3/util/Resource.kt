package com.benhagy.gamefinder3.util

// resource wrapper for error handling for non-paginated api calls

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(val isLoading: Boolean = true, data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}