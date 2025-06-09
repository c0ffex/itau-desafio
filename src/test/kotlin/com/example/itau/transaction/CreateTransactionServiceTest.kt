package com.example.itau.transaction

import com.example.itau.transaction.dtos.TransactionRequest
import com.example.itau.transaction.repositories.TransactionRepository
import com.example.itau.transaction.services.CreateTransactionService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateTransactionServiceTest {

    private val transactionRepository = mockk<TransactionRepository>()
    private val service = CreateTransactionService(transactionRepository)

    @Test
    fun `execute should save transaction with formatted value and current time`() {
        val request = TransactionRequest(valor = 15.50)
        val expectedTransaction =
                Transaction(valor = (request.valor * 100).toLong(), horaData = LocalDateTime.now())

        every { transactionRepository.save(any()) } answers { firstArg() }

        val result = service.execute(request)

        verify(exactly = 1) {
            transactionRepository.save(match { it.valor == expectedTransaction.valor })
        }
        assertEquals(expectedTransaction.valor, result.valor)
    }
}
