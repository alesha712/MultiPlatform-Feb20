package com.hqs.alx.sharedmodule

import kotlinx.coroutines.CoroutineDispatcher

expect fun platformName(): String

fun createApplicationScreenMessage() : String {
    return "Hello World, from ${platformName()}"
}

internal expect val Main: CoroutineDispatcher
internal expect val Background: CoroutineDispatcher