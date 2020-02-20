package com.hqs.alx.sharedmodule.repo

import com.hqs.alx.sharedmodule.ApplicationDispatcher
import com.hqs.alx.sharedmodule.api.ConversionsApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import model.ConversionRates

class ConversionsRepo {

    private val client = HttpClient()
    private val api : ConversionsApi = ConversionsApi()

    fun getConversionRates(callBack: (ConversionRates) -> Unit){
        GlobalScope.apply {
            launch(ApplicationDispatcher) {
                val json: String = client.get{
                    url(api.baseUrl)
                }

                val conversionObj = Json.nonstrict.parse(ConversionRates.serializer(), json)
                callBack(conversionObj)
            }
        }
    }
}