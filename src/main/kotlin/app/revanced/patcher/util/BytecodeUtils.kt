package app.revanced.patcher.util

/**
 * Matches two lists of parameters, where the first parameter list
 * starts with the values of the second list.
 */
internal fun parametersStartsWith(
    targetMethodParameters: Iterable<CharSequence>,
    fingerprintParameters: Iterable<CharSequence>,
): Boolean {
    if (fingerprintParameters.count() != targetMethodParameters.count()) return false
    val fingerprintIterator = fingerprintParameters.iterator()

    targetMethodParameters.forEach {
        if (!it.startsWith(fingerprintIterator.next())) return false
    }

    return true
}