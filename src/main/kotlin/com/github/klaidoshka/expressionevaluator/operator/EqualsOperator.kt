package com.github.klaidoshka.expressionevaluator.operator

import com.github.klaidoshka.expressionevaluator.ExpressionOperator
import java.math.BigDecimal

/**
 * Operator for the equals comparison operation. Handles both number and string comparison.
 */
object EqualsOperator : ExpressionOperator {
    override val operator = "=="

    override fun evaluate(left: Any, right: Any): Boolean {
        return if (left is Number && right is Number) {
            val leftDecimal = left as? BigDecimal ?: left.toDouble().toBigDecimal()
            val rightDecimal = right as? BigDecimal ?: right.toDouble().toBigDecimal()
            leftDecimal.compareTo(rightDecimal) == 0
        } else {
            left == right
        }
    }
}