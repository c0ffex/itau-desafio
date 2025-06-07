package com.example.itau.transaction

import com.example.itau.transaction.repositories.TransactionRepository
import com.example.itau.transaction.services.FindTransactionsInPeriod
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FindTransactionsInPeriodTest {

    private val transactionRepository: TransactionRepository = mockk()

    private val service = FindTransactionsInPeriod(transactionRepository)

    @Test
    fun `execute should return transactions after cutoff time`() {
        val minutes = 5L
        val now = LocalDateTime.now()

        val validTransactions = listOf(
            Transaction(id = 1, valor = 1000, horaData = now.minusMinutes(3)),
            Transaction(id = 2, valor = 2000, horaData = now.minusMinutes(1)),
            Transaction(id = 4, valor = 2100, horaData = now.minusMinutes(10))
        )

        every { transactionRepository.getTransactionsInPeriod(any()) } returns validTransactions

        val result = service.execute(minutes)
        println("Service execution result: $result")

        verify(exactly = 1) { transactionRepository.getTransactionsInPeriod(any()) }
        assertEquals(validTransactions, result)
    }


}
