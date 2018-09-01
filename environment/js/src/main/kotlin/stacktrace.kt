package sample

var Throwable.stack: String
    inline get() = asDynamic().stack.toString()
    internal inline set(value) {
        asDynamic().stack = value
    }

fun captureStackTrace(drop: Int = 0): String {
    check(drop > 0)

    //language=JavaScript
    val jsCode = """
        // noinspection JSUnusedLocalSymbols
        var getStackTrace = function() {
            // noinspection JSUnusedLocalSymbols
            var obj = {};

            // noinspection JSUnresolvedFunction
            Error.captureStackTrace(obj, getStackTrace);

            return obj.stack;
        };

        getStackTrace()"""
    val result = js(jsCode) as String

    return result.lineSequence().drop(2 + drop).joinToString("\n")
}

fun Throwable.printStackTrace(message: String? = null) {
    if (message != null) {
        console.error(message)
    }

    if (stack.none { it == '\n' }) {
        stack += "\n    at untracked stack (????:??:??)"
    }

    stack += "\n\n  Captured: \n" + captureStackTrace(1)

    console.error(this)
}

fun Throwable.captureStackTrace(): String {
    val captured = captureStackTrace(1)
    if (stack.isNotBlank()) {
        stack += "\n"
    }

    stack += captured
    return captured
}

inline fun <T> tryCatchingAnything(block: () -> T): T {
    try {
        return block()
    } catch (e: dynamic) {
        throw (e as? Throwable)?.let { JavaScriptException(it) }
                ?: (e as? String)?.let { JavaScriptException(it) }
                ?: JavaScriptException(
                        if (e.message != null)
                            (e.message as Any).toString()
                        else
                            "An object has been thrown. See 'extra' for details.",
                        extra = e as Any
                )
    }
}
