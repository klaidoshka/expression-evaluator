### 📜 Introduction

This is a simple expression evaluator that can evaluate expressions with created and provided
operators, pre-processors, post-processors.

Provided text in expressions must be surrounded by double quotes to **keep** the text as it is,
otherwise whitespace is trimmed and the result may not be as expected.

```kotlin
fun main() {
    // Creating an instance that wraps around a few parameters
    val expressions = Expressions()

    check(expressions.evaluate("true || false")) // true
    check(!expressions.evaluate("true && false")) // false
    check(expressions.evaluate("true && false || true")) // true
    check(expressions.evaluate("\"hot mom in town\" contains \"mom\"")) // true

    // Set parameters to use in further evaluations
    expressions.setParameters(
        mapOf(
            "dadSaid" to "Great Job Son",
            "age" to 18
        )
    )

    // \"dadSaid\" is replaced with \"Great Job Son\" when evaluating
    check(expressions.evaluate("dadSaid == \"Great Job Son\"")) // true
    check(!expressions.evaluate("dadSaid == Great Job Son")) // false, because of whitespace trimming
    check(expressions.evaluate("age >= 18")) // true

    expressions.setParameter("age" to 17)

    check(!expressions.evaluate("age >= 18")) // false
}
```

### 🟰 Case-insensitive comparison

In case it is wanted to compare strings in case-insensitive way with `IgnoreCasePreprocessor`,
then `ReplacementsPreprocessor` should be provided with string parameters to replace strings
beforehand. This may or may not be reworked in the future to support case-insensitive
comparison at the parameters' resolution.

### 😎 Extendable

You can add `ExpressionPreprocessor`, `ExpressionOperator` and `ExpressionPostprocessor` to extend
this simple expression evaluator even further.