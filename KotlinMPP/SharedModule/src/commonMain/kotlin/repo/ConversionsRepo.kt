package com.hqs.alx.sharedmodule.repo

import com.hqs.alx.sharedmodule.api.ConversionsApi

class ConversionsRepo (private val api : ConversionsApi) {

    suspend fun getLastConversions() : String{
        return api.getLatestConversions()
    }
}