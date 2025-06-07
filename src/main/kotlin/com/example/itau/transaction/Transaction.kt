package com.example.itau.transaction

import jakarta.persistence.*

import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
data class Transaction (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val valor: Long,
    val horaData: LocalDateTime
)