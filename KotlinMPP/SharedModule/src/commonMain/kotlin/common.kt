package com.hqs.alx.sharedmodule

import kotlinx.coroutines.CoroutineDispatcher

expect fun platformName(): String
internal expect val ApplicationDispatcher: CoroutineDispatcher

fun createApplicationScreenMessage() : String {
    return "Hello World, from ${platformName()}"
}

expect fun makeLog(text: String)