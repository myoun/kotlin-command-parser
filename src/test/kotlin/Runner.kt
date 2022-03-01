import live.myoun.kcp.Command
import live.myoun.kcp.GlobalCommandManager

fun main() {
    GlobalCommandManager.registerCommand(Sum)
    GlobalCommandManager.executeCommand("/sum 1.2 2.8")
}

object Sum : Command("sum", "add") {

    init {
        addArg<Double>("a")
        addArg<Double>("b")
    }

    override fun execute(args: Map<String, *>) {
        println(args["a"] as Double + args["b"] as Double)
    }
}