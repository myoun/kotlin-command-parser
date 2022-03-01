package live.myoun.kcp

import kotlin.reflect.KClass

/**
 * Command
 */
abstract class Command(val name: String, vararg val aliases: String) {

    val args = mutableListOf<Map<String, KClass<*>>>()

    inline fun <reified T> addArg(arg: String) {
        args.add(mapOf(arg to T::class))
    }

    abstract fun execute(args: Map<String, *>)
}