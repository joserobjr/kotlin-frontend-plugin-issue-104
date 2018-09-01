package sample

class JavaScriptException(message: String? = null, cause: Throwable? = null, val extra: Any? = null)
    : Exception(message ?: "No message has been informed", cause) {
    constructor(message: String) : this(message, null)
    constructor(cause: Throwable) : this(cause.message ?: cause.stack.takeIf { '\n' !in it }, cause = cause)
}
