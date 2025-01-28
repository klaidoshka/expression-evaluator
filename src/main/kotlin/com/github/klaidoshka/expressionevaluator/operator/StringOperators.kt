package com.github.klaidoshka.expressionevaluator.operator

import com.github.klaidoshka.expressionevaluator.ExpressionOperator

/**
 * Operator that defines string contains operation between two values: left contains right
 */
object ContainsOperator : ExpressionOperator {
    override val operator = "contains"
    override fun evaluate(left: Any, right: Any): Boolean {
        return left.toString().contains(right.toString())
    }
}

/**
 * Operator that defines string endsWith operation between two values: left equals right
 */
object EndsWithOperator : ExpressionOperator {
    override val operator = "endsWith"
    override fun evaluate(left: Any, right: Any): Boolean {
        return left.toString().endsWith(right.toString())
    }
}

/**
 * Operator that defines string startsWith operation between two values: left equals right
 */
object StartsWithOperator : ExpressionOperator {
    override val operator = "startsWith"
    override fun evaluate(left: Any, right: Any): Boolean {
        return left.toString().startsWith(right.toString())
    }
}