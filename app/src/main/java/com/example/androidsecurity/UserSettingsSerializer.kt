package com.example.androidsecurity

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import java.io.InputStream
import java.io.OutputStream


class UserSettingsSerializer(private val cryptoManager: CryptoManager) : Serializer<UserSettings> {
    override val defaultValue: UserSettings
        get() = UserSettings()

    override suspend fun readFrom(input: InputStream): UserSettings {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            decodeFromString(
                UserSettings.serializer(),
                decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(userSettings: UserSettings, output: OutputStream) {
        cryptoManager.encrypt(
            Json.encodeToString(
                UserSettings.serializer(),
                userSettings
            ).encodeToByteArray(),
            output
        )
    }

}