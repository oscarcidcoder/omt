package com.omt.omtest.utils

/**
 * Handler responses utility
 */
sealed class Result<out T> {
    data class Success<T>(val value: T): Result<T>()
    data class Error(val message: String?, val throwable: Throwable?): Result<Nothing>()
    object Loading : Result<Nothing>()

    companion object Factory{
        inline fun <T> build(function: () -> T): Result<T> =
            try {
               Success(function.invoke())
            } catch (e: Exception) {
                Error(e.message,e)
            }
    }

}