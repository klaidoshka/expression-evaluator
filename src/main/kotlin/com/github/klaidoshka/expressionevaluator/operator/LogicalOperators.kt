package com.github.klaidoshka.expressionevaluator.operator

import com.github.klaidoshka.expressionevaluator.ExpressionOperator

/**
 * Operator that defines the logical AND operation between two values: left AND right
 */
object AndOperator : ExpressionOperator {
    override val operator = "&&"
    override fun evaluate(left: Any, right: Any): Boolean {
        return try {
            left as Boolean && right as Boolean
        } catch (e: ClassCastException) {
            throw IllegalArgumentException("Invalid operands for operator $operator: $left, $right")
        }
    }
}

/**
 * Operator for the logical OR operation between two values: left OR right
 */
object OrOperator : ExpressionOperator {
    override val operator = "||"
    override fun evaluate(left: Any, right: Any): Boolean {
        return try {
            left as Boolean || right as Boolean
        } catch (e: ClassCastException) {
            throw IllegalArgumentException("Invalid operands for operator $operator: $left, $right")
        }
    }
}