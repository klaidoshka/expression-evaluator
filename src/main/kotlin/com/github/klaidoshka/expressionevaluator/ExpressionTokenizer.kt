package com.github.klaidoshka.expressionevaluator

/**
 * Tokenize an expression into a list of tokens that can be evaluated by the [Expressions]
 */
object ExpressionTokenizer {
    /**
     * Tokenize the given expression into a list of tokens
     *
     * @param expression to tokenize
     *
     * @return list of tokens
     */
    fun tokenize(expression: String, operators: Set<String>): List<String> {
        val tokens = mutableListOf<String>()
        val buffer = StringBuilder()
        var i = 0

        fun addBufferAsToken() {
            if (buffer.isNotEmpty()) {
                tokens.add(buffer.toString())
                buffer.clear()
            }
        }

        while (i < expression.length) {
            when (val char = expression[i]) {
                ' ', '\t' -> Unit

                '(', ')' -> {
                    addBufferAsToken()
                    tokens.add(char.toString())
                }

                else -> {
                    var foundOperator: String? = null
                    
                    for (operator in operators) {
                        if (expression.startsWith(operator, i)) {
                            foundOperator = operator
                            break
                        }
                    }

                    if (foundOperator != null) {
                        addBufferAsToken()
                        tokens.add(foundOperator)
                        i += foundOperator.length - 1 // Adjust index for operator length
                    } else {
                        buffer.append(char)
                    }
                }
            }
            
            i++
        }

        // Add any remaining buffer content as a token
        addBufferAsToken()

        return tokens
    }
}