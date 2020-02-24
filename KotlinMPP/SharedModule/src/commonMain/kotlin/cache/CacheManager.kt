package com.hqs.alx.sharedmodule.cache

import com.hqs.alx.sharedmodule.model.ConversionsModel
import com.hqs.alx.sharedmodule.responses.ConversionRates

object CacheManager {
    var baseRatesJson: String = ""
    var currenciesList: ArrayList<String> = arrayListOf()
    var exchangeRatesDictionary: HashMap<String, HashMap<String, String>> = hashMapOf()

    fun saveBaseJsonAndRatedDictionary(json: String, list: ArrayList<String>) {
        this.baseRatesJson = json
        currenciesList = list
        createDictionary(list)
    }

    private fun createDictionary(rates: ArrayList<String>?) {
        if (rates.isNullOrEmpty())
            return

        rates.forEach {
            exchangeRatesDictionary[it] = hashMapOf()
        }
    }

    fun addRateToHash(base: String, value: String, exchangeRate: String) {
        if (!exchangeRatesDictionary[base].isNullOrEmpty()) {
            val hash = exchangeRatesDictionary[base]!!
            hash[value] = exchangeRate
            exchangeRatesDictionary[base] = hash
        } else {
            val hash = hashMapOf<String, String>()
            hash[value] = exchangeRate
            exchangeRatesDictionary[base] = hash
        }

    }

    fun isRatesListAvailable(): Boolean {
        return !currenciesList.isNullOrEmpty()
    }

    fun isExchangeRateAvailable(base: String, value: String): Boolean {
        return (!exchangeRatesDictionary[base].isNullOrEmpty() && !exchangeRatesDictionary[base]!![value].isNullOrEmpty())
    }

    fun getSpecificExchangeRate(base: String, value: String): String {
        return exchangeRatesDictionary[base]?.get(value) ?: "0"
    }
}