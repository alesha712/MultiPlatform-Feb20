package com.hqs.alx.sharedmodule.responses

import kotlinx.serialization.Serializable

@Serializable
data class ConversionRates(
	var error : ErrorException? = null,
	val date: String? = null,
	val rates: HashMap<String, String>? = null,
	val base: String? = null,
	var currenciesList: ArrayList<String> = arrayListOf()
) {
	fun createRatesList() {
		rates?.forEach {
			currenciesList.add(it.key)
		}
	}

	@Serializable
	inner class ErrorException(
		var statusCode: String? = null,
		var message: String? = null
	){
		fun setError(statusCode: String?, message: String?){
			this.statusCode = statusCode
			this.message = message
		}
	}
}