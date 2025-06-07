package com.example.itau.statistics.services

import com.example.itau.statistics.StatisticsResult
import com.example.itau.statistics.exceptions.NoTransactionsFoundException
import com.example.itau.transaction.Transaction
import com.example.itau.transaction.services.FindTransactionsInPeriod
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class CalculateStatistics (
    private val findTransactionsInPeriod: FindTransactionsInPeriod
) {
    fun execute(minutes: Long = 1): StatisticsResult {
        val transactions: List<Transaction> = findTransactionsInPeriod.execute(minutes)

        val count = transactions.size

        val values = transactions.map { it.getValorAsDecimal() }

        if (transactions.isEmpty()) {
            throw NoTransactionsFoundException("No transactions found in the last $minutes minutes")
        }

        val sum = values.fold(BigDecimal.ZERO) { acc, value -> acc + value }
        val avg = if (count > 0) sum.divide(BigDecimal(count), 3, RoundingMode.HALF_EVEN) else BigDecimal.ZERO
        val min = values.minOrNull() ?: BigDecimal.ZERO
        val max = values.maxOrNull() ?: BigDecimal.ZERO

        return StatisticsResult(
            count = count,
            sum = sum,
            avg = avg,
            min = min,
            max = max
        )
    }
}