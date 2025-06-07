package com.example.itau.statistics

import com.example.itau.statistics.exceptions.NoTransactionsFoundException
import com.example.itau.statistics.services.CalculateStatistics
import com.example.itau.transaction.Transaction
import com.example.itau.transaction.services.FindTransactionsInPeriod
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class CalculateStatisticsTest {

    private val findTransactionsInPeriod: FindTransactionsInPeriod = mockk()

    private val service = CalculateStatistics(findTransactionsInPeriod)

    @Test
    fun `execute should calculate statistics correctly`() {
        val now = LocalDateTime.now()
        val transactions = listOf(
            Transaction(id = 1, valor = 1000, horaData = now),
            Transaction(id = 2, valor = 2000, horaData = now),
            Transaction(id = 3, valor = 3000, horaData = now)
        )

        every { findTransactionsInPeriod.execute(any()) } returns transactions

        val result = service.execute()

        assertEquals(3, result.count)
        assertEquals(BigDecimal("60.00"), result.sum) // cents = 60.00 dollars
        assertEquals(BigDecimal("20.000"), result.avg) // average of 60/3 = 20.000
        assertEquals(BigDecimal("10.00"), result.min) // 1000 cents = 10.00 dollars
        assertEquals(BigDecimal("30.00"), result.max) // 3000 cents = 30.00 dollars
    }

    @Test
    fun `execute should throw exception when no transactions found`() {
        every { findTransactionsInPeriod.execute(any()) } returns emptyList()

        val exception = assertThrows(NoTransactionsFoundException::class.java) {
            service.execute(5)
        }

        assertEquals("No transactions found in the last 5 minutes", exception.message)
    }
}
