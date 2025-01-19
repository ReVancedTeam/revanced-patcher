package app.revanced.patcher.patch

import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * An option.
 *
 * @param T The value type of the option.
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param type The type of the option value (to handle type erasure).
 * @param validator The function to validate the option value.
 *
 * @constructor Create a new [Option].
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class Option<T>
@PublishedApi
@Deprecated("Use the constructor with the name instead of a key instead.")
internal constructor(
    @Deprecated("Use the name property instead.")
    val key: String,
    val default: T? = null,
    val values: Map<String, T?>? = null,
    @Deprecated("Use the name property instead.")
    val title: String? = null,
    val description: String? = null,
    val required: Boolean = false,
    val type: KType,
    val validator: Option<T>.(T?) -> Boolean = { true },
) {
    /**
     * The name.
     */
    val name = key

    /**
     * An option.
     *
     * @param T The value type of the option.
     * @param name The name.
     * @param default The default value.
     * @param values Eligible option values mapped to a human-readable name.
     * @param description A description.
     * @param required Whether the option is required.
     * @param type The type of the option value (to handle type erasure).
     * @param validator The function to validate the option value.
     *
     * @constructor Create a new [Option].
     */
    @PublishedApi
    internal constructor(
        name: String,
        default: T? = null,
        values: Map<String, T?>? = null,
        description: String? = null,
        required: Boolean = false,
        type: KType,
        validator: Option<T>.(T?) -> Boolean = { true },
    ) : this(name, default, values, name, description, required, type, validator)

    /**
     * The value of the [Option].
     */
    var value: T?
        /**
         * Set the value of the [Option].
         *
         * @param value The value to set.
         *
         * @throws OptionException.ValueRequiredException If the value is required but null.
         * @throws OptionException.ValueValidationException If the value is invalid.
         */
        set(value) {
            assertRequiredButNotNull(value)
            assertValid(value)

            uncheckedValue = value
        }

        /**
         * Get the value of the [Option].
         *
         * @return The value.
         *
         * @throws OptionException.ValueRequiredException If the value is required but null.
         * @throws OptionException.ValueValidationException If the value is invalid.
         */
        get() {
            assertRequiredButNotNull(uncheckedValue)
            assertValid(uncheckedValue)

            return uncheckedValue
        }

    // The unchecked value is used to allow setting the value without validation.
    private var uncheckedValue = default

    /**
     * Reset the [Option] to its default value.
     * Override this method if you need to mutate the value instead of replacing it.
     */
    fun reset() {
        uncheckedValue = default
    }

    private fun assertRequiredButNotNull(value: T?) {
        if (required && value == null) throw OptionException.ValueRequiredException(this)
    }

    private fun assertValid(value: T?) {
        if (!validator(value)) throw OptionException.ValueValidationException(value, this)
    }

    override fun toString() = value.toString()

    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ) = value

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: T?,
    ) {
        this.value = value
    }
}

/**
 * A collection of [Option]s where options can be set and retrieved by their key.
 *
 * @param options The options.
 *
 * @constructor Create a new [Options].
 */
class Options internal constructor(
    private val options: Map<String, Option<*>>,
) : Map<String, Option<*>> by options {
    internal constructor(options: Set<Option<*>>) : this(options.associateBy { it.name })

    /**
     * Set an option's value.
     *
     * @param key The key.
     * @param value The value.
     *
     * @throws OptionException.OptionNotFoundException If the option does not exist.
     */
    operator fun <T : Any> set(key: String, value: T?) {
        val option = this[key]

        try {
            @Suppress("UNCHECKED_CAST")
            (option as Option<T>).value = value
        } catch (e: ClassCastException) {
            throw OptionException.InvalidValueTypeException(
                value?.let { it::class.java.name } ?: "null",
                option.value?.let { it::class.java.name } ?: "null",
            )
        }
    }

    /**
     * Get an option.
     *
     * @param key The key.
     *
     * @return The option.
     */
    override fun get(key: String) = options[key] ?: throw OptionException.OptionNotFoundException(key)
}

/**
 * Create a new [Option] with a string value.
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun stringOption(
    key: String,
    default: String? = null,
    values: Map<String, String?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<String>.(String?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a string value and add it to the current [PatchBuilder].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun PatchBuilder<*>.stringOption(
    key: String,
    default: String? = null,
    values: Map<String, String?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<String>.(String?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with an integer value.
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun intOption(
    key: String,
    default: Int? = null,
    values: Map<String, Int?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<Int>.(Int?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with an integer value and add it to the current [PatchBuilder].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun PatchBuilder<*>.intOption(
    key: String,
    default: Int? = null,
    values: Map<String, Int?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<Int>.(Int?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a boolean value.
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun booleanOption(
    key: String,
    default: Boolean? = null,
    values: Map<String, Boolean?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<Boolean>.(Boolean?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a boolean value and add it to the current [PatchBuilder].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun PatchBuilder<*>.booleanOption(
    key: String,
    default: Boolean? = null,
    values: Map<String, Boolean?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<Boolean>.(Boolean?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a float value.
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun floatOption(
    key: String,
    default: Float? = null,
    values: Map<String, Float?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<Float>.(Float?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a float value and add it to the current [PatchBuilder].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun PatchBuilder<*>.floatOption(
    key: String,
    default: Float? = null,
    values: Map<String, Float?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<Float>.(Float?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a long value.
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun longOption(
    key: String,
    default: Long? = null,
    values: Map<String, Long?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<Long>.(Long?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a long value and add it to the current [PatchBuilder].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun PatchBuilder<*>.longOption(
    key: String,
    default: Long? = null,
    values: Map<String, Long?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<Long>.(Long?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a string list value.
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun stringsOption(
    key: String,
    default: List<String>? = null,
    values: Map<String, List<String>?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<List<String>>.(List<String>?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a string list value and add it to the current [PatchBuilder].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun PatchBuilder<*>.stringsOption(
    key: String,
    default: List<String>? = null,
    values: Map<String, List<String>?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<List<String>>.(List<String>?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with an integer list value.
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun intsOption(
    key: String,
    default: List<Int>? = null,
    values: Map<String, List<Int>?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<List<Int>>.(List<Int>?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with an integer list value and add it to the current [PatchBuilder].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun PatchBuilder<*>.intsOption(
    key: String,
    default: List<Int>? = null,
    values: Map<String, List<Int>?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<List<Int>>.(List<Int>?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a boolean list value.
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun booleansOption(
    key: String,
    default: List<Boolean>? = null,
    values: Map<String, List<Boolean>?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<List<Boolean>>.(List<Boolean>?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a boolean list value and add it to the current [PatchBuilder].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun PatchBuilder<*>.booleansOption(
    key: String,
    default: List<Boolean>? = null,
    values: Map<String, List<Boolean>?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<List<Boolean>>.(List<Boolean>?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a float list value and add it to the current [PatchBuilder].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun PatchBuilder<*>.floatsOption(
    key: String,
    default: List<Float>? = null,
    values: Map<String, List<Float>?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<List<Float>>.(List<Float>?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a long list value.
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun longsOption(
    key: String,
    default: List<Long>? = null,
    values: Map<String, List<Long>?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<List<Long>>.(List<Long>?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option] with a long list value and add it to the current [PatchBuilder].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
fun PatchBuilder<*>.longsOption(
    key: String,
    default: List<Long>? = null,
    values: Map<String, List<Long>?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    validator: Option<List<Long>>.(List<Long>?) -> Boolean = { true },
) = option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
)

/**
 * Create a new [Option].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
inline fun <reified T> option(
    key: String,
    default: T? = null,
    values: Map<String, T?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    noinline validator: Option<T>.(T?) -> Boolean = { true },
) = Option(
    key,
    default,
    values,
    title,
    description,
    required,
    typeOf<T>(),
    validator,
)

/**
 * Create a new [Option] and add it to the current [PatchBuilder].
 *
 * @param key The key.
 * @param default The default value.
 * @param values Eligible option values mapped to a human-readable name.
 * @param title The title.
 * @param description A description.
 * @param required Whether the option is required.
 * @param validator The function to validate the option value.
 *
 * @return The created [Option].
 *
 * @see Option
 */
inline fun <reified T> PatchBuilder<*>.option(
    key: String,
    default: T? = null,
    values: Map<String, T?>? = null,
    title: String? = null,
    description: String? = null,
    required: Boolean = false,
    noinline validator: Option<T>.(T?) -> Boolean = { true },
) = app.revanced.patcher.patch.option(
    key,
    default,
    values,
    title,
    description,
    required,
    validator,
).also { it() }

/**
 * An exception thrown when using [Option]s.
 *
 * @param errorMessage The exception message.
 */
sealed class OptionException(errorMessage: String) : Exception(errorMessage, null) {
    /**
     * An exception thrown when a [Option] is set to an invalid value.
     *
     * @param invalidType The type of the value that was passed.
     * @param expectedType The type of the value that was expected.
     */
    class InvalidValueTypeException(invalidType: String, expectedType: String) : OptionException("Type $expectedType was expected but received type $invalidType")

    /**
     * An exception thrown when a value did not satisfy the value conditions specified by the [Option].
     *
     * @param value The value that failed validation.
     */
    class ValueValidationException(value: Any?, option: Option<*>) : OptionException("The option value \"$value\" failed validation for ${option.name}")

    /**
     * An exception thrown when a value is required but null was passed.
     *
     * @param option The [Option] that requires a value.
     */
    class ValueRequiredException(option: Option<*>) : OptionException("The option ${option.name} requires a value, but the value was null")

    /**
     * An exception thrown when a [Option] is not found.
     *
     * @param key The key of the [Option].
     */
    class OptionNotFoundException(key: String) : OptionException("No option with key $key")
}
