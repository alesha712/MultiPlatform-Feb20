package model

import kotlinx.serialization.Serializable

@Serializable
data class ConversionRates(

	val date: String? = null,
	val rates: HashMap<String, String>? = null,
	val base: String? = null
)