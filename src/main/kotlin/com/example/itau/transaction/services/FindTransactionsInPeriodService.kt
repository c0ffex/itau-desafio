package com.example.itau.transaction.services

import com.example.itau.transaction.Transaction
import com.example.itau.transaction.repositories.TransactionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class FindTransactionsInPeriod(
    private val transactionRepository: TransactionRepository
) {
    fun execute(minutes: Long): List<Transaction> {
        val cutoff = LocalDateTime.now().minusMinutes(minutes)
        return transactionRepository.getTransactionsInPeriod(cutoff)
    }
}