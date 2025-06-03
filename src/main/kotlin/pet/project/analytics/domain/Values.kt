package pet.project.analytics.domain

import java.util.UUID

@JvmInline
value class UserId(val value: UUID)

@JvmInline
value class ProductId(val value: UUID)

@JvmInline
value class OrderId(val value: UUID)