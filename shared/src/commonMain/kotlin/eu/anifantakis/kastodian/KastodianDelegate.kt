package eu.anifantakis.kastodian

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Allows Kastodian to be used with property delegation.
 *
 * Usage:
 * ```
 * val kastodian: Kastodian = // ... obtain your Kastodian instance
 * var mySetting: String by kastodian(defaultValue = "default", encrypted = true)
 * ```
 *
 * @param T The type of the property.
 * @param defaultValue The default value to return if the key is not found.
 * @param encrypted Whether the value should be encrypted (defaults to true).
 * @return A ReadWriteProperty delegate.
 */
inline operator fun <reified T> Kastodian.invoke(
    defaultValue: T,
    encrypted: Boolean = true
): ReadWriteProperty<Any?, T> {
    // 'this' inside this inline function refers to the Kastodian instance
    // on which 'invoke' is called.
    val kastodianInstance = this

    return object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            // Because this object expression is inside an inline function with reified T,
            // T is reified here. We explicitly pass it to getDirect.
            return kastodianInstance.getDirect<T>(property.name, defaultValue, encrypted)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            // Similarly, T is reified here. We explicitly pass it to putDirect.
            kastodianInstance.putDirect<T>(property.name, value, encrypted)
        }
    }
}