package com.example.itau.transaction.services

import com.example.itau.transaction.Transaction
import com.example.itau.transaction.repositories.TransactionRepository
import com.example.itau.transaction.dtos.TransactionRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CreateTransactionService(
    private val transactionRepository: TransactionRepository
) {
    @Transactional
    fun execute(request: TransactionRequest): Transaction {
        val formattedValue = (request.valor*100).toLong()
        val transactionTime = LocalDateTime.now()
        return transactionRepository.save(Transaction(valor = formattedValue, horaData = transactionTime))
    }
}