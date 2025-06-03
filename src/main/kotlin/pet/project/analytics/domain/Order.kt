package pet.project.analytics.domain

import java.math.BigDecimal
import java.time.Instant

data class Order(
    val id: OrderId,
    val userId: UserId,
    val productId: ProductId,
    val quantity: Int,
    val total: BigDecimal,
    val status: OrderStatus,
    val createdAt: Instant
)

enum class OrderStatus {
    CREATED,
    COMPLETED,
    CANCELLED
}