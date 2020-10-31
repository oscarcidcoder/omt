package com.omt.omtest.utils

inline fun <reified T> Result<T>.doIfFailure(callback: (error: String?, throwable: Throwable?) -> Unit) {
    if (this is Result.Error) {
        callback(message, throwable)
    }
}

inline fun <reified T> Result<T>.doIfSuccess(callback: (value: T) -> Unit) {
    if (this is Result.Success) {
        callback(value)
    }
}

// Convierte el externalId en paramentro para buscar los recomendados
fun String.externalToParam() = "external_content_id:$this"