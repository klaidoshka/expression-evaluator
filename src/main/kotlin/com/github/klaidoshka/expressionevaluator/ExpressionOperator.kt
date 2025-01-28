package com.github.klaidoshka.expressionevaluator

/**
 * Expression's operator that can be evaluated by the [Expressions] for two operands
 */
interface ExpressionOperator {
    /**
     * Expression's operator that this instance represents and evaluates
     */
    val operator: String

    /**
     * Evaluate the operator for the given operands
     *
     * @param left operand
     * @param right operand
     *
     * @return the result of the operator evaluation
     */
    fun evaluate(left: Any, right: Any): Boolean
}