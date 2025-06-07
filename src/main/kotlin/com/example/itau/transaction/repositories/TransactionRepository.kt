package com.example.itau.transaction.repositories

import com.example.itau.transaction.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface TransactionRepository : JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.horaData > :time")
    fun getTransactionsInPeriod(@Param("time") time: LocalDateTime): List<Transaction>
}