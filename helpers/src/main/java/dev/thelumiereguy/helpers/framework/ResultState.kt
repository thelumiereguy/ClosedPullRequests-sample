package dev.thelumiereguy.helpers.framework

sealed class ResultState<out Type> {

    data class Success<Type>(
        val data: Type
    ) : ResultState<Type>()

    data class Loading<Type>(
        val data: Type? = null
    ) : ResultState<Type>()

    data class Error<Type>(
        val message: String?,
        val data: Type? = null
    ) : ResultState<Type>()

    companion object {

        inline fun <reified Type> loading(existingData: Type?) = Loading(existingData)

        inline fun <reified Type> error(existingData: Type, message: String?) = Error(
            message,
            existingData
        )
    }
}
