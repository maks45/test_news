package com.md.testnews.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsSource(
    @SerialName("id") val id: String?,
    @SerialName("name") val name: String
)