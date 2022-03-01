package live.myoun.kcp

import kotlin.reflect.cast

/**
 * You Should Implement This!!!
 */
abstract class CommandManager {

    private val commands = hashMapOf<String, Command>()
    private val aliases = hashMapOf<String, String>()

    fun registerCommand(command: Command) {
        commands[command.name] = command
        for (alias in command.aliases) {
            aliases[alias] = command.name
        }
    }

    fun parse(command: String) : Pair<Command, Map<String, *>> {
        if (!command.startsWith("/")) {
            throw Exception("`$command` is not a command!")
        }

        val splited = command.split(" ")
        val name = splited[0].substring(1)

        val cmd: Command = try {
            commands[name]!!
        } catch (e: Exception) {
            try {
                commands[aliases[name]]!!
            } catch (e2: Exception) {
                throw Exception("Command $name not found")
            }
        }

        val args = splited.subList(1, splited.size)

        if (args.size != cmd.args.size) {
            throw Exception("args size not same")
        }

        val result = hashMapOf<String, Any>()

        for ((i, arg) in args.withIndex()) {
            val reqarg = cmd.args[i]

            val reqtype = reqarg.values.first()
            val parsed = when (reqtype.qualifiedName) {
                "kotlin.Int" -> arg.toInt()
                "kotlin.String" -> arg
                "kotlin.Double" -> arg.toDouble()
                "kotlin.Long" -> arg.toLong()
                "kotlin.Float" -> arg.toFloat()
                "kotlin.Byte" -> arg.toByte()
                else -> {
                    reqtype.cast(arg)
                }
            }

            result[reqarg.keys.first()] = parsed
        }
        return cmd to result
    }

    fun executeCommand(command: String) {
        val (cmd, parsed) = parse(command)
        cmd.execute(parsed)
    }
}

/**
 * For Real Use
 */
object GlobalCommandManager : CommandManager()

