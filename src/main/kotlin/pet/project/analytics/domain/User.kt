package pet.project.analytics.domain

import pet.project.analytics.exception.InvalidEmailException
import pet.project.analytics.utils.require
import java.time.Instant

data class User(
    val id: UserId,
    val email: Email,
    val fullName: String,
    val createdAt: Instant,
)

data class Email(val email: String) {
    init {
        require(isValidEmail(email), InvalidEmailException::class) {
            "Invalid email address: $email"
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
}