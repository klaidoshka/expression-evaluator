package com.github.klaidoshka.expressionevaluator

/**
 * Postprocessor that post-processes an expression after it is tokenized, before it is evaluated
 */
interface ExpressionPostprocessor {
    /**
     * Postprocess an expression after it is tokenized, before it is evaluated
     *
     * @param expression to postprocess
     *
     * @return post-processed expression
     */
    fun postprocess(expression: String): String
}