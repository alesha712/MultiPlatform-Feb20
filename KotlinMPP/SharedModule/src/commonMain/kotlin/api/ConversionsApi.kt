package com.hqs.alx.sharedmodule.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.Url


class ConversionsApi {

    val baseUrl = Url("https://api.exchangeratesapi.io/latest")
}