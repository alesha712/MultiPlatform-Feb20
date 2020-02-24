package com.hqs.alx.sharedmodule.utils

fun convertRatesWithAmount(exchangeRate: Double, amount: Double): String {
    return (amount * exchangeRate).toString()
}