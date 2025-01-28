package com.github.klaidoshka.expressionevaluator.preprocessor

import com.github.klaidoshka.expressionevaluator.ExpressionPreprocessor

/**
 * Preprocessor that replaces the given strings in the expression
 *
 * @property replacements to apply to the expression
 */
class ReplacementsPreprocessor(
    private val replacements: Map<String, String>
) : ExpressionPreprocessor {
    override fun preprocess(expression: String): String {
        return replacements.entries.fold(expression) { acc, (key, value) ->
            acc.replace("\\b$key\\b".toRegex(), value)
        }
    }

    companion object {

        /**
         * Create a new replacements preprocessor with the given replacements being
         * filtered into a map of strings, where the key is the string to replace
         * and the value is the replacement string (if the value is not a string, it is ignored)
         *
         * @param replacements to apply to the expression
         *
         * @return a new replacements preprocessor
         */
        fun create(replacements: () -> Map<String, Any>): ReplacementsPreprocessor {
            return ReplacementsPreprocessor(
                replacements()
                    .asSequence()
                    .filter { (_, value) -> value is String }
                    .map { (key, value) -> key to (value as String) }
                    .toMap()
            )
        }
    }
}