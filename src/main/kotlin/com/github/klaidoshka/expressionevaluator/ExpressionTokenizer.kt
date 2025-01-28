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
        val operatorsSorted = operators.sortedByDescending { it.length }
        val tokens = mutableListOf<String>()
        val buffer = StringBuilder()
        var i = 0
        var insideQuotes = false
        var quoteChar: Char? = null

        fun addBufferAsToken() {
            if (buffer.isNotEmpty()) {
                tokens.add(buffer.toString())
                buffer.clear()
            }
        }

        while (i < expression.length) {
            val char = expression[i]

            if (insideQuotes) {
                // Append everything inside quotes
                if (char == quoteChar) {
                    insideQuotes = false
                    buffer.append("\"")
                    addBufferAsToken()
                } else {
                    buffer.append(char)
                }
            } else {
                when (char) {
                    ' ', '\t' -> Unit

                    '(', ')' -> {
                        addBufferAsToken()
                        tokens.add(char.toString())
                    }
                    
                    '"', '\'' -> {
                        addBufferAsToken()
                        insideQuotes = true
                        quoteChar = char
                        buffer.append("\"")
                    }

                    else -> {
                        var foundOperator: String? = null

                        for (operator in operatorsSorted) {
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
            }

            i++
        }

        // Add any remaining buffer content as a token
        addBufferAsToken()

        return tokens
    }
}