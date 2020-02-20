package model

import kotlinx.serialization.Serializable

@Serializable
data class ConversionRates(

	val date: String? = null,
	val rates: Rates? = null,
	val base: String? = null
)