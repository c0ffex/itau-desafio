package com.example.itau

import com.example.itau.transaction.Transaction
import com.example.itau.transaction.repositories.TransactionRepository
import com.example.itau.transaction.services.FindTransactionsInPeriod
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import java.time.LocalDateTime

@DataJpaTest
@Import(FindTransactionsInPeriod::class)
class FindTransactionsInPeriodIntegrationTest @Autowired constructor(
    private val transactionRepository: TransactionRepository,
    private val findTransactionsInPeriod: FindTransactionsInPeriod
) {
    @Test
    fun `should return only transactions after cutoff time`() {
        val now = LocalDateTime.now()

        // Save transactions with different timestamps
        val recentTransaction1 = transactionRepository.save(Transaction(valor = 1000, horaData = now.minusMinutes(2)))
        val recentTransaction2 = transactionRepository.save(Transaction(valor = 2000, horaData = now.minusMinutes(4)))
        val oldTransaction = transactionRepository.save(Transaction(valor = 3000, horaData = now.minusMinutes(10)))

        val minutes = 5L

        // Execute service to get transactions in last 5 minutes
        val result = findTransactionsInPeriod.execute(minutes)

        println("Transactions returned by service: $result")
        // Assert only recent transactions are returned
        assertTrue(result.contains(recentTransaction1))
        assertTrue(result.contains(recentTransaction2))
        assertTrue(result.none { it.id == oldTransaction.id })
    }
}