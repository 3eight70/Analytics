package pet.project.analytics.exception

sealed class CustomException(
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException()

data class InvalidEmailException(
    override val message: String
) : CustomException(message)