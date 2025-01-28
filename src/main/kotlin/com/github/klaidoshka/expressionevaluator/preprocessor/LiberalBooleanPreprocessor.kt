package com.github.klaidoshka.expressionevaluator.preprocessor

import com.github.klaidoshka.expressionevaluator.ExpressionPreprocessor

/**
 * Preprocessor that converts various boolean-like values to "true" or "false"
 */
object LiberalBooleanPreprocessor : ExpressionPreprocessor {
    /**
     * Regex to match boolean values
     */
    private val BOOLEAN_REGEX = "\\b(true|false|on|off|yes|no)\\b"
        .toRegex(RegexOption.IGNORE_CASE)

    /**
     * Map to convert various boolean-like values to "true" or "false"
     */
    private val booleanMapping = mapOf(
        "true" to "true",
        "on" to "true",
        "yes" to "true",
        "false" to "false",
        "off" to "false",
        "no" to "false"
    )

    override fun preprocess(expression: String): String {
        return expression.replace(BOOLEAN_REGEX) {
            val matchedValue = it.value.lowercase()
            booleanMapping[matchedValue] ?: matchedValue
        }
    }
}