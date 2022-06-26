package dev.thelumiereguy.helpers.framework

sealed class APIState<out Type> {

    data class Success<Type>(
        val data: Type
    ) : APIState<Type>()

    object Loading : APIState<Nothing>()

    data class Error(
        val message: String?
    ) : APIState<Nothing>()

    inline infix fun <reified Type> appendAPIState(
        defaultData: Type,
    ): ResultState<Type> {
        return when (this) {
            is Error -> {
                ResultState.error(
                    defaultData,
                    this.message,
                )
            }
            Loading -> {
                ResultState.loading(
                    defaultData
                )
            }
            is Success -> {
                ResultState.Success(
                    defaultData
                )
            }
        }
    }
}
