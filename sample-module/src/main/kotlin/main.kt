package sample

import kotlinx.coroutines.experimental.launch
import module.sourceMapSupport.SourceMapSupport
import kotlin.coroutines.experimental.suspendCoroutine

suspend fun forceError() {
    suspendCoroutine<Nothing> {

        console.info("SUCCESS! We are running Kotlin inside NodeJS!")

        console.info(CoreObject.test)

        console.info("Forcing an exception to test the tryCatchingAnything, JavaScriptException and the stacktrace")
        console.info("The stacktrace should link to the kt files since sourceMaps has been activated.")
        console.info("Also note that I'm throwing a string instead of an Error ;)")
        //language=JavaScript
        js("""throw "Kotlin/JS's catch can't catch strings like this unless it catches dynamic." """) as Nothing?
    }
}

suspend fun start() {
    forceError()
}

fun main(args: Array<String>) {
    tryCatchingAnything {
        SourceMapSupport.install()
    }

    launch {
        tryCatchingAnything {
            start()
        }
    }

}
