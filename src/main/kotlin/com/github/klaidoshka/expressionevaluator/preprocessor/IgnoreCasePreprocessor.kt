package com.github.klaidoshka.expressionevaluator.preprocessor

import com.github.klaidoshka.expressionevaluator.ExpressionPreprocessor

/**
 * Preprocessor that converts the expression's comparison to ignore case
 */
object IgnoreCasePreprocessor : ExpressionPreprocessor {
    override fun preprocess(expression: String): String {
        return expression.replace(REGEX) {
            val (left, operator, right, ignoreCase) = it.destructured

            if (ignoreCase.isNotBlank()) {
                "${left.lowercase()} $operator ${right.lowercase()}"
            } else {
                it.value
            }
        }
    }

    // Regex to match a comparison expression
    private val REGEX = "([\\w\"']+)\\s*(==|!=)\\s*([\\w\"']+)\\s*(-i)?".toRegex()
}