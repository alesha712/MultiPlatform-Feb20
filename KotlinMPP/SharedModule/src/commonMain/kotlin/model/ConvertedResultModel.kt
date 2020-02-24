package com.hqs.alx.sharedmodule.model

import kotlinx.serialization.Serializable

@Serializable
data class ConvertedResultModel(
    var error: ErrorException? = null,
    var result: String? = null
) {
    fun setError(statusCode: String, message: String) {
        this.error = ErrorException(statusCode, message)
    }
}