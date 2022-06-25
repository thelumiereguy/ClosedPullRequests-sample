package dev.thelumiereguy.data.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClosedPRDetailsResponse(
    @SerialName("number")
    val number: Int,
    @SerialName("comments")
    val comments: Int,
    @SerialName("commits")
    val commits: Int ,
    @SerialName("changed_files")
    val changed_files: Int
)