package com.github.klaidoshka.expressionevaluator

/**
 * Preprocessor that preprocesses an expression before it is tokenized and evaluated
 */
interface ExpressionPreprocessor {
    /**
     * Preprocess an expression before it is tokenized and evaluated
     *
     * @param expression to preprocess
     *
     * @return preprocessed expression
     */
    fun preprocess(expression: String): String
}
