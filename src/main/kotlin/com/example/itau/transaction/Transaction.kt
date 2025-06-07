package com.example.itau.transaction

import jakarta.persistence.*
import java.math.BigDecimal
import java.math.RoundingMode

import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
data class Transaction (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val valor: Long,
    val horaData: LocalDateTime

) {
    fun getValorAsDecimal(): BigDecimal {
        return BigDecimal(valor).divide(BigDecimal(100), 2, RoundingMode.HALF_EVEN)
    }
}