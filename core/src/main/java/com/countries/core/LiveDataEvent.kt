package com.countries.core


/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class LiveDataEvent<out T>(private val content: T) {

  companion object {
    fun empty() = LiveDataEvent(Unit)
  }

  private var hasBeenHandled = false

  fun consume(action: (T) -> Unit = {}) {
    if (!hasBeenHandled) {
      hasBeenHandled = true
      action(content)
    }
  }

  /**
   * Returns the content and prevents its use again.
   */
  fun hasBeenHandled(): Boolean = hasBeenHandled

  /**
   * Returns the content, even if it's already been handled.
   */
  fun peekContent(): T? = content
}