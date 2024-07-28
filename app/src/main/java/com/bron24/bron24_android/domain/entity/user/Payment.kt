package com.bron24.bron24_android.domain.entity.user

import com.bron24.bron24_android.domain.entity.enums.PaymentMethod
import com.bron24.bron24_android.domain.entity.enums.PaymentStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class Payment(
    val paymentId: String,
    val booking: Booking,
    val amount: BigDecimal,
    val method: PaymentMethod,
    val paymentDate: LocalDateTime,
    val status: PaymentStatus
)