package com.example.itau.transaction.controllers

import com.example.itau.transaction.dtos.TransactionRequest
import com.example.itau.transaction.services.CreateTransactionService
import com.example.itau.transaction.services.FindTransactionsInPeriod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transacao")
class TransactionController(
    private val transactionService: CreateTransactionService.TransactionService,
    private val findTransactionsService: FindTransactionsInPeriod
) {
    @PostMapping
    fun postTransaction(@RequestBody transactionRequest: TransactionRequest): ResponseEntity<Any> {
        val createdTransaction = transactionService.execute(transactionRequest)

        return ResponseEntity.ok(createdTransaction)
    }

    @GetMapping
    fun getTransactionsInPeriod(@RequestParam("minutes") minutes: Long): ResponseEntity<Any> {
        val transactions = findTransactionsService.execute(minutes)
        return ResponseEntity.ok(transactions)
    }
}