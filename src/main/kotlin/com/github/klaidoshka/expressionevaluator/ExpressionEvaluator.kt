package com.github.klaidoshka.expressionevaluator

import com.github.klaidoshka.expressionevaluator.datastructure.PeekingIterator

/**
 * Evaluate a string expression with the given parameters
 *
 * @return the result of the expression evaluation
 */
object ExpressionEvaluator {

    /**
     * Evaluate a string expression with the given operators and parameters
     *
     * @param tokens to evaluate
     * @param operators to use in the expression
     * @param parameters to use in the expression
     *
     * @return the result of the expression evaluation
     * @throws IllegalArgumentException if the expression is invalid
     */
    fun evaluateExpression(
        tokens: PeekingIterator<String>,
        operators: Map<String, ExpressionOperator>,
        parameters: Map<String, Any>
    ): Boolean {
        val result = evaluateLogicalOr(tokens, operators, parameters)

        while (tokens.hasNext()) {
            when (val token = tokens.next()) {
                ")" -> break // End of a grouped expression
                else -> throw IllegalArgumentException("Unexpected token: $token")
            }
        }

        return result
    }

    /**
     * Evaluate a logical OR expression
     *
     * @param tokens to evaluate
     * @param operators to use in the expression
     * @param parameters to use in the expression
     *
     * @return the result of the expression evaluation
     */
    private fun evaluateLogicalOr(
        tokens: PeekingIterator<String>,
        operators: Map<String, ExpressionOperator>,
        parameters: Map<String, Any>
    ): Boolean {
        var result = evaluateLogicalAnd(tokens, operators, parameters)

        while (tokens.hasNext() && tokens.peek() == "||") {
            tokens.next() // Consume "||"
            if (result) {
                // Drop the rest of the expression if the result is already true
                // $left $operator $right
                tokens.asSequence().drop(3).any()
            } else {
                result = evaluateLogicalAnd(tokens, operators, parameters)
            }
        }

        return result
    }

    /**
     * Evaluate a logical AND expression
     *
     * @param tokens to evaluate
     * @param operators to use in the expression
     * @param parameters to use in the expression
     *
     * @return the result of the expression evaluation
     */
    private fun evaluateLogicalAnd(
        tokens: PeekingIterator<String>,
        operators: Map<String, ExpressionOperator>,
        parameters: Map<String, Any>
    ): Boolean {
        var result = compare(tokens, operators, parameters)

        while (tokens.hasNext() && tokens.peek() == "&&") {
            tokens.next() // Consume "&&"
            result = result && compare(tokens, operators, parameters)
        }

        return result
    }

    /**
     * Evaluate a comparison expression
     *
     * @param tokens to evaluate
     * @param operators to use in the expression
     * @param parameters to use in the expression
     *
     * @return the result of the expression evaluation
     */
    private fun compare(
        tokens: PeekingIterator<String>,
        operators: Map<String, ExpressionOperator>,
        parameters: Map<String, Any>
    ): Boolean {
        val left = toInstance(tokens, operators, parameters)

        if (!tokens.hasNext()) {
            return left as? Boolean ?: throw IllegalArgumentException("Invalid expression")
        }

        val operator = tokens.next()
        val right = toInstance(tokens, operators, parameters)
        val result = operators[operator]?.evaluate(left, right)
        
        return result ?: try {
            when (operator) {
                "&&" -> left as Boolean && right as Boolean
                "||" -> left as Boolean || right as Boolean
                else -> throw IllegalArgumentException("Invalid comparison: $left $operator $right")
            }
        } catch (e: ClassCastException) {
            throw IllegalArgumentException("Invalid comparison: $left $operator $right")
        }
    }

    /**
     * Convert the next token to the appropriate type
     *
     * @param tokens to evaluate
     * @param parameters to use in the expression
     *
     * @return the result of the expression evaluation
     * @throws IllegalArgumentException if the token is not of a valid type or the expression is invalid
     */
    private fun toInstance(
        tokens: PeekingIterator<String>,
        operators: Map<String, ExpressionOperator>,
        parameters: Map<String, Any>
    ): Any {
        if (!tokens.hasNext()) {
            throw IllegalArgumentException("Unexpected end of expression")
        }

        val token = tokens.next()

        return when {
            // Negation
            token == "!" -> !toInstance(tokens, operators, parameters).toString().toBoolean()
            // Grouped expressions
            token == "(" -> evaluateExpression(tokens, operators, parameters)
            // Variables in context
            token in parameters -> parameters[token]!!
            // Boolean literals
            token == "true" || token == "false" -> token.toBoolean()
            // Numeric literals
            token.toBigDecimalOrNull() != null -> token.toBigDecimalOrNull()!!
            // Unknown token
            else -> token.removeSurrounding("\"")
        }
    }
}