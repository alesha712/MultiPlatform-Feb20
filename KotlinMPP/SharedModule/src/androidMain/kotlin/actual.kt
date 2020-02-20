package com.hqs.alx.sharedmodule

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual fun platformName(): String {
    return "Android PERETZ"
}

//internal actual val Main: CoroutineDispatcher = Dispatchers.Main
//internal actual val Background: CoroutineDispatcher = Dispatchers.Default
//actual fun getConversions(): String {
//   return ""
//}
internal actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Default