package com.hqs.alx.sharedmodule.repo

import com.hqs.alx.sharedmodule.ApplicationDispatcher
import com.hqs.alx.sharedmodule.api.ConversionsApi
import com.hqs.alx.sharedmodule.cache.CacheManager
import com.hqs.alx.sharedmodule.makeLog
import com.hqs.alx.sharedmodule.model.ConversionsModel
import com.hqs.alx.sharedmodule.model.ConvertedResultModel
import com.hqs.alx.sharedmodule.responses.ConversionRates
import com.hqs.alx.sharedmodule.utils.*
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.ParserException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ConversionsRepo {

    private val client = HttpClient()
    private val api: ConversionsApi = ConversionsApi()
    private val cacheManager: CacheManager = CacheManager


    fun getConversionRates(callBack: (ConversionsModel) -> Unit) {
        CoroutineScope(ApplicationDispatcher).launch {
            //This is the model that will be returned to the client
            val conversions = ConversionsModel()
            if (CacheManager.isRatesListAvailable()) {
                conversions.currenciesList = CacheManager.currenciesList
                callBack(conversions)
            } else {
                try {
                    val json = client.get<String>(api.baseUrl) {}
                    val conversionJsonObj = Json.nonstrict.parse(ConversionRates.serializer(), json)
                    conversions.createRatesList(conversionJsonObj.rates)

                    CacheManager.saveBaseJsonAndRatedDictionary(json, conversions.currenciesList)
                    callBack(conversions)
                } catch (e: ResponseException) {
                    conversions.setError(e.response.status.value.toString(), BASE_RESPONSE_ERROR)
                    makeLog(e.response.status.description)
                    callBack(conversions)
                } catch (e: Throwable) {
                    conversions.setError(UNKNOWN_ERROR_CODE, BASE_RESPONSE_ERROR)
                    makeLog(e.message ?: UNKNOWN_ERROR_MESSAGE)
                } catch (e: Exception) {
                    conversions.setError(UNKNOWN_ERROR_CODE, BASE_RESPONSE_ERROR)
                    makeLog(e.message ?: UNKNOWN_ERROR_MESSAGE)
                    callBack(conversions)
                }
            }
        }
    }

    private fun getExchangeRate(base: String, value: String, callBack: (String) -> Unit) {
        CoroutineScope(ApplicationDispatcher).launch {
            if (CacheManager.isExchangeRateAvailable(base, value)) {
                makeLog("CACHE")
                callBack(CacheManager.getSpecificExchangeRate(base, value))
            } else {
                makeLog("API")
                try {
                    val json = client.get<String>(api.exchangeRate) {
                        parameter(EXCHANGE_BASE, base)
                        parameter(EXCHANGE_SYMBOLS, value)
                        makeLog("$SERVER_LOG_SENDING_REQUEST : ${this.method} - ${this.url}")
                    }
                    val conversionJsonObj = Json.nonstrict.parse(ConversionRates.serializer(), json)
                    makeLog("$SERVER_LOG_RESPONSE : ${api.exchangeRate} ---> ${HttpStatusCode.OK.value} === $json")
                    CacheManager.addRateToHash(
                        base,
                        value,
                        conversionJsonObj.rates?.get(value) ?: ""
                    )

                    callBack(conversionJsonObj.rates?.get(value) ?: "0")
                } catch (e: ResponseException) {
                    val url = e.message?.substringAfter("")
                    val a = url?.substringBefore(")")
                    makeLog("$SERVER_LOG_RESPONSE $a ----> ${e.response.status.value} : ${e.response.status.description}")
                    callBack("0")
                } catch (e: Throwable) {
                    makeLog(e.message ?: UNKNOWN_ERROR_MESSAGE)
                    callBack("0")
                } catch (e: Exception) {
                    makeLog(e.message ?: UNKNOWN_ERROR_MESSAGE)
                    callBack("0")
                }
            }
        }
    }

    fun convertRates(
        base: String,
        convertTo: String,
        amount: String,
        callBack: (ConvertedResultModel) -> Unit
    ) {
        var exchangeRate: String
        getExchangeRate(base, convertTo) {
            exchangeRate = it

            var doubleAmount = 0.0
            var doubleExchangeRate = 0.0

            val convertedModel = ConvertedResultModel()
            try {
                doubleAmount = amount.toDouble()
                doubleExchangeRate = exchangeRate.toDouble()
            } catch (e: ParserException) {
                makeLog(e.message ?: PARSING_EXCEPTION)
                convertedModel.setError(UNKNOWN_ERROR_CODE, e.message ?: PARSING_EXCEPTION)
            } catch (e: Exception) {
                makeLog(e.message ?: PARSING_EXCEPTION)
                convertedModel.setError(UNKNOWN_ERROR_CODE, e.message ?: PARSING_EXCEPTION)
            }

            val exchangedAmount = convertRatesWithAmount(doubleExchangeRate, doubleAmount)

            convertedModel.result = exchangedAmount

            callBack(convertedModel)
        }
    }
}