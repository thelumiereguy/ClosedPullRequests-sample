package dev.thelumiereguy.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClosedPRsResponseItem(
    @SerialName("closed_at") val closedAt: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("number") val number: Int,
    @SerialName("title") val title: String,
    @SerialName("base") val base: Base,
    @SerialName("head") val head: Head,
    @SerialName("user") val user: User,
) {
    @Serializable
    data class Base(
        @SerialName("ref") val label: String,
    )

    @Serializable
    data class Head(
        @SerialName("ref") val label: String,
    )

    @Serializable
    data class User(
        @SerialName("login") val login: String,
        @SerialName("avatar_url") val avatar_url: String
    )
}
