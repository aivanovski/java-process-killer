package com.github.ai.jpkiller.domain.usecases

import com.github.ai.jpkiller.domain.output.OutputPrinter
import com.github.ai.jpkiller.entity.Either
import com.github.ai.jpkiller.entity.ProcessGroup
import com.github.ai.jpkiller.entity.ProcessType
import java.lang.Exception
import java.util.LinkedList
import java.util.Scanner

class AskToKillUseCase {

    fun getPidsToKill(
        groups: List<ProcessGroup>,
        printer: OutputPrinter
    ): Either<List<Int>> {
        printer.println("")
        printer.println("Available commands:")

        if (groups.size > 1) {
            printer.println("    a - stop All")
        }

        for (group in groups) {
            when {
                group.types.contains(ProcessType.ANDROID_STUDIO) -> {
                    printer.println("    s - stop Android Studio")
                }
                group.types.contains(ProcessType.INTELLIJ_IDEA) -> {
                    printer.println("    i - stop Intellij Idea")
                }
                group.types.contains(ProcessType.KOTLIN_LANGUAGE_SERVER) -> {
                    printer.println("    k - stop Kotlin Language Server")
                }
                group.types.contains(ProcessType.KOTLIN_DAEMON) ||
                    group.types.contains(ProcessType.GRADLE_DAEMON) -> {
                    printer.println("    g - stop Gradle Daemon")
                }
                group.types.contains(ProcessType.UNKNOWN) -> {
                    printer.println("    o - stop Other")
                }
            }
        }

        printer.println("")
        printer.print("Enter command to stop process: ")

        val scanner = Scanner(System.`in`)
        val command = scanner.nextLine()

        val typesToKill = parseCommand(command.trim().lowercase())
        if (typesToKill.isLeft()) {
            return typesToKill.mapToLeft()
        }

        val types = typesToKill.unwrap()
        val pids = groups.flatMap { group -> group.processes }
            .mapNotNull { process ->
                if (types.contains(process.type)) {
                    process.pid
                } else {
                    null
                }
            }

        return Either.Right(pids)
    }

    private fun parseCommand(command: String): Either<Set<ProcessType>> {
        if (command == "a") {
            return Either.Right(ProcessType.values().toSet())
        }

        val queue = LinkedList(command.toList())
        if (queue.isEmpty()) {
            return Either.Right(emptySet())
        }

        val result = mutableSetOf<ProcessType>()
        while (queue.isNotEmpty()) {
            val nextTypes = ARGUMENT_TO_PROCESS_TYPE_MAP[queue.poll()]
                ?: return Either.Left(Exception("Unknown command"))

            result.addAll(nextTypes)
        }

        return Either.Right(result)
    }

    companion object {
        private val ARGUMENT_TO_PROCESS_TYPE_MAP = mapOf(
            's' to setOf(ProcessType.ANDROID_STUDIO),
            'i' to setOf(ProcessType.INTELLIJ_IDEA),
            'g' to setOf(ProcessType.GRADLE_DAEMON, ProcessType.KOTLIN_DAEMON),
            'k' to setOf(ProcessType.KOTLIN_LANGUAGE_SERVER),
            'o' to setOf(ProcessType.UNKNOWN)
        )
    }
}