package com.hqs.alx.sharedmodule.model

import kotlinx.serialization.Serializable

@Serializable
data class ConversionsModel(
    var error: ErrorException? = null,
    var currenciesList: ArrayList<String> = arrayListOf()
) {

    fun createRatesList(rates: HashMap<String, String>?) {
        if (rates.isNullOrEmpty())
            return

        rates.forEach {
            currenciesList.add(it.key)
        }
    }

    fun setError(statusCode: String, message: String) {
        this.error = ErrorException(statusCode, message)
    }
}