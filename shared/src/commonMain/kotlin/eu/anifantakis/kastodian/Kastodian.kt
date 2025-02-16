package eu.anifantakis.kastodian

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty



/**
 * An API for secure key–value storage.
 *
 * The default behavior is to encrypt data.
 */
expect class Kastodian {
    inline fun <reified T> getDirect(key: String, defaultValue: T, encrypted: Boolean = true): T
    inline fun <reified T> putDirect(key: String, value: T, encrypted: Boolean = true)

    suspend inline fun <reified T> get(key: String, defaultValue: T, encrypted: Boolean = true): T
    suspend inline fun <reified T> put(key: String, value: T, encrypted: Boolean = true)

    suspend fun delete(key: String)
    fun deleteDirect(key: String)
}
