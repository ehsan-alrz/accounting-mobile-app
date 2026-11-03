package com.example.balanceapp

import java.math.BigDecimal

data class Account(
    val id: Long,
    val accountNumber: String,
    val balance: BigDecimal,
    val currency: String
)