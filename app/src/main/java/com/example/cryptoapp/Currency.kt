package com.example.cryptoapp

class Currency(
    val id: String,
    val symbol: String,
    val name: String,
    val nameid: String,
    val rank: Long,
    val priceUsd: String,
    val percentChange24H: String,
    val percentChange1H: String,
    val percentChange7D: String,
    val priceBtc: String,
    val marketCapUsd: String,
    val volume24: Double,
    val volume24A: Double,
    val csupply: String,
    val tsupply: String? = null,
    val msupply: String? = null
)
