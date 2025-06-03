package pet.project.analytics.utils

import pet.project.analytics.exception.CustomException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

@OptIn(ExperimentalContracts::class)
inline fun require(value: Boolean, exceptionToThrow: KClass<out CustomException>, lazyMessage: () -> Any) {
    contract {
        returns() implies value
    }
    if (!value) {
        val message = lazyMessage()

        throw exceptionToThrow.primaryConstructor!!.call(message.toString())
    }
}