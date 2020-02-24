package com.hqs.alx.sharedmodule.model

import kotlinx.serialization.Serializable

@Serializable
class ErrorException(
    var statusCode: String = "",
    var message: String = ""
)