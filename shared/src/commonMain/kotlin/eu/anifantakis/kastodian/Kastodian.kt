package eu.anifantakis.kastodian

/**
 * An API for secure key–value storage.
 *
 * The default behavior is to encrypt data.
 */
expect class Kastodian {
    inline fun <reified T> getDirect(key: String, defaultValue: T, encrypted: Boolean = true): T
    inline fun <reified T> putDirect(key: String, value: T, encrypted: Boolean = true)
}
