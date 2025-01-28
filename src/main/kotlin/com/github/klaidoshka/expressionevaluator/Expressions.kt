package com.github.klaidoshka.expressionevaluator

import com.github.klaidoshka.expressionevaluator.datastructure.PeekingIterator
import com.github.klaidoshka.expressionevaluator.operator.*
import com.github.klaidoshka.expressionevaluator.preprocessor.IgnoreCasePreprocessor
import com.github.klaidoshka.expressionevaluator.preprocessor.LiberalBooleanPreprocessor

class Expressions(
    operators: List<ExpressionOperator> = DEFAULT_OPERATORS,
    parameters: Map<String, Any> = emptyMap(),
    preprocessors: List<ExpressionPreprocessor> = DEFAULT_PREPROCESSORS,
    postprocessors: List<ExpressionPostprocessor> = emptyList()
) {
    // Map of operators by their symbol
    private val operators = operators.associateBy { it.operator }.toMutableMap()

    // Map of parameters by their name
    private val parameters = parameters.toMutableMap()

    // List of preprocessors to apply to the expression before evaluation
    private val preprocessors = preprocessors.toMutableList()

    // List of postprocessors to apply to the expression after tokenization
    private val postprocessors = postprocessors.toMutableList()

    /**
     * Evaluate a string expression with the given parameters and preprocessors
     *
     * @param expression to evaluate
     *
     * @return the result of the expression evaluation
     */
    fun evaluate(expression: String): Boolean {
        val preprocessedExpression = preprocessors.fold(expression) { acc, preprocessor ->
            preprocessor.preprocess(acc)
        }

        var tokens = ExpressionTokenizer.tokenize(preprocessedExpression, operators.keys)

        postprocessors.forEach { postprocessor ->
            tokens = tokens.map {
                postprocessor.postprocess(it)
            }
        }

        return ExpressionEvaluator.evaluateExpression(
            PeekingIterator(tokens.iterator()),
            operators,
            parameters
        )
    }

    companion object {
        // Default operators to use in the expression
        val DEFAULT_OPERATORS = listOf(
            AndOperator,
            ContainsOperator,
            EndsWithOperator,
            EqualsOperator,
            GreaterOperator,
            GreaterOrEqualOperator,
            LesserOperator,
            LesserOrEqualOperator,
            NotEqualsOperator,
            OrOperator,
            StartsWithOperator
        )

        // Default preprocessors to use in the expression
        val DEFAULT_PREPROCESSORS = listOf(
            IgnoreCasePreprocessor,
            LiberalBooleanPreprocessor
        )
    }
}