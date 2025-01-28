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

    /**
     * Sets an operator to the expressions' evaluator. If the operator already exists, it will be
     * overwritten.
     *
     * @param operator to set
     */
    fun setOperator(operator: ExpressionOperator) {
        operators[operator.operator] = operator
    }

    /**
     * Set the operators to use in the expression
     *
     * @param operators to use
     */
    fun setOperators(operators: List<ExpressionOperator>) {
        this.operators.clear()
        operators.forEach { operator -> setOperator(operator) }
    }

    /**
     * Sets a parameter to the expressions' evaluator. If the parameter already exists, it will be
     * overwritten.
     *
     * @param parameter to set
     */
    fun setParameter(parameter: Pair<String, Any>) {
        parameters[parameter.first] = parameter.second
    }

    /**
     * Set the parameters to use in the expression
     *
     * @param parameters to use
     */
    fun setParameters(parameters: Map<String, Any>) {
        this.parameters.clear()
        parameters.forEach { (name, value) -> setParameter(name to value) }
    }

    /**
     * Add a preprocessor to the expressions' evaluator
     *
     * @param preprocessor to add
     */
    fun addPreprocessor(preprocessor: ExpressionPreprocessor) {
        preprocessors.add(preprocessor)
    }

    /**
     * Set the preprocessors to use in the expression
     *
     * @param preprocessors to use
     */
    fun setPreprocessors(preprocessors: List<ExpressionPreprocessor>) {
        this.preprocessors.clear()
        preprocessors.forEach { preprocessor -> addPreprocessor(preprocessor) }
    }

    /**
     * Add a postprocessor to the expressions' evaluator
     *
     * @param postprocessor to add
     */
    fun addPostprocessor(postprocessor: ExpressionPostprocessor) {
        postprocessors.add(postprocessor)
    }

    /**
     * Set the postprocessors to use in the expression
     *
     * @param postprocessors to use
     */
    fun setPostprocessors(postprocessors: List<ExpressionPostprocessor>) {
        this.postprocessors.clear()
        postprocessors.forEach { postprocessor -> addPostprocessor(postprocessor) }
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