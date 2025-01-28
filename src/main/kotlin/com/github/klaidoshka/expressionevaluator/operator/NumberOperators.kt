package com.github.klaidoshka.expressionevaluator.operator

import com.github.klaidoshka.expressionevaluator.ExpressionOperator
import java.math.BigDecimal

/**
 * Operator for the equals comparison operation
 */
object LesserOperator : NumberExpressionOperator {
    override val operator = "<"
}

/**
 * Operator for the greater comparison operation
 */
object GreaterOperator : NumberExpressionOperator {
    override val operator = ">"
}

/**
 * Operator for the lesser or equal comparison operation
 */
object LesserOrEqualOperator : NumberExpressionOperator {
    override val operator = "<="
}

/**
 * Operator for the greater or equal comparison operation
 */
object GreaterOrEqualOperator : NumberExpressionOperator {
    override val operator = ">="
}

/**
 * Number comparison operator interface that asserts that the given values are numbers
 * before comparing them with the given operator
 */
private interface NumberExpressionOperator : ExpressionOperator {
    override fun evaluate(left: Any, right: Any): Boolean {
        assertNumbers(left, right)
        return compareNumbers(left as Number, right as Number, operator)
    }

    /**
     * Assert that the given [left] and [right] values are numbers
     *
     * @throws IllegalArgumentException if either [left] or [right] is not a number
     */
    private fun assertNumbers(left: Any, right: Any) {
        if (left !is Number || right !is Number) {
            throw IllegalArgumentException("Invalid comparison between non-numbers: $left == $right")
        }
    }

    /**
     * Compare two numbers with the given operator
     *
     * @param left value
     * @param right value
     * @param operator to use for comparison
     *
     * @return the result of the comparison
     * @throws IllegalArgumentException if the operator is invalid
     */
    private fun compareNumbers(left: Number, right: Number, operator: String): Boolean {
        val leftDecimal = left as? BigDecimal ?: left.toDouble().toBigDecimal()
        val rightDecimal = right as? BigDecimal ?: right.toDouble().toBigDecimal()
        return when (operator) {
            ">" -> leftDecimal > rightDecimal
            "<" -> leftDecimal < rightDecimal
            ">=" -> leftDecimal >= rightDecimal
            "<=" -> leftDecimal <= rightDecimal
            else -> throw IllegalArgumentException("Unknown operator: $operator")
        }
    }
}