package com.example.fs_android.utils

import com.example.fs_android.domain.model.user.User
import kotlinx.serialization.SerializationException
import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.json.Json


class UserSettingsSerializer() : Serializer<User> {
    override val defaultValue: User get() = User()

    override suspend fun readFrom(input: InputStream): User {
        // TODO:
        return try {
            Json.decodeFromString(deserializer = User.serializer(),string = input.readBytes().decodeToString())
        }catch (e:SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: User, output: OutputStream) {
        // TODO:
        output.write(
            Json.encodeToString(
                serializer = User.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}