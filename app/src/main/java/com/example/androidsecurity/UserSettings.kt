package com.example.androidsecurity

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val username: String? = null,
    val password: String? = null,
)