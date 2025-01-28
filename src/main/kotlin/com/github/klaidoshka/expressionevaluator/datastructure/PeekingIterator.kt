package com.github.klaidoshka.expressionevaluator.datastructure

/**
 * Iterator that allows peeking at the next item without consuming it
 */
class PeekingIterator<T>(private val iterator: Iterator<T>) : Iterator<T> {
    /**
     * Next item that has been peeked but not consumed
     */
    private var next: T? = null

    override fun hasNext(): Boolean {
        return next != null || iterator.hasNext()
    }

    override fun next(): T {
        return if (next != null) {
            val token = next!!
            next = null
            token
        } else {
            iterator.next()
        }
    }

    /**
     * Peek at the next item without consuming it
     *
     * @return next item or null if there are no more items
     */
    fun peek(): T? {
        if (next == null && iterator.hasNext()) {
            next = iterator.next()
        }
        return next
    }

    /**
     * Push back an item to be consumed next
     *
     * @param item to push back
     */
    fun pushBack(item: T) {
        if (next != null) throw IllegalStateException("Cannot push back more than one token")
        next = item
    }
}