package com.example.itau.statistics.controllers

import com.example.itau.statistics.services.CalculateStatistics
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/estatistica")
class GetStatisticsController(
    private val calculateStatistics: CalculateStatistics
) {
    @GetMapping
    fun getTransactionsInPeriod(@RequestParam("minutes") minutes: Long): ResponseEntity<Any> {
        val transactions = calculateStatistics.execute(minutes)
        return ResponseEntity.ok(transactions)
    }
}