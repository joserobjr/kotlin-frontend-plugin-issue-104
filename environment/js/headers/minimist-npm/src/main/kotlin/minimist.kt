package module.minimist

import kotlin.js.Json

@JsModule("minimist")
external fun minimist(array: Array<String>, opts: dynamic = definedExternally): Args

external interface Args : Json

val Args.unnamed: Array<String> get() = get("_").unsafeCast<Array<String>>()
