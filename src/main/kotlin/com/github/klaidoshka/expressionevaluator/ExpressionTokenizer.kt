package com.github.klaidoshka.expressionevaluator

/**
 * Tokenize an expression into a list of tokens that can be evaluated by the [Expressions]
 */
object ExpressionTokenizer : (String) -> List<String> {
    /**
     * Tokenize the given expression into a list of tokens
     *
     * @param expression to tokenize
     *
     * @return list of tokens
     */
    override fun invoke(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        val buffer = StringBuilder()
        var i = 0

        while (i < expression.length) {
            when (val char = expression[i]) {
                ' ', '\t' -> Unit

                '(', ')', '!', '&', '|', '=', '<', '>' -> {
                    if (buffer.isNotEmpty()) {
                        tokens.add(buffer.toString())
                        buffer.clear()
                    }

                    when (char) {
                        '=', '!', '<', '>' -> {
                            if (i + 1 < expression.length && expression[i + 1] == '=') {
                                tokens.add("$char=")
                                i++
                            } else {
                                tokens.add(char.toString())
                            }
                        }

                        '&', '|' -> {
                            if (i + 1 < expression.length && expression[i + 1] == char) {
                                tokens.add("$char$char")
                                i++
                            } else {
                                tokens.add(char.toString())
                            }
                        }

                        else -> tokens.add(char.toString())
                    }
                }

                else -> buffer.append(char)
            }
            i++
        }

        if (buffer.isNotEmpty()) {
            tokens.add(buffer.toString())
        }

        return tokens
    }
}