package com.example.itau.statistics

import java.math.BigDecimal

data class StatisticsResult(
    val count: Int,
    val sum: BigDecimal,
    val avg: BigDecimal,
    val min: BigDecimal,
    val max: BigDecimal
)