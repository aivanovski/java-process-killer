package com.github.ai.jpk.domain.usecases

import com.github.ai.jpk.domain.ProcessExecutor
import com.github.ai.jpk.entity.Either
import com.github.ai.jpk.entity.ProcessGroup
import com.github.ai.jpk.entity.ProcessType
import java.util.Scanner

class AskToKillUseCase(
    private val processExecutor: ProcessExecutor
) {

    fun getPidsToKill(groups: List<ProcessGroup>): Either<List<Int>> {
        println("")
        println("Available commands:")

        if (groups.size > 1) {
            println("    a - stop All")
        }

        for (group in groups) {
            when {
                group.types.contains(ProcessType.ANDROID_STUDIO) -> {
                    println("    s - stop Android Studio")
                }
                group.types.contains(ProcessType.INTELLIJ_IDEA) -> {
                    println("    i - stop Intellij Idea")
                }
                group.types.contains(ProcessType.KOTLIN_LANGUAGE_SERVER) -> {
                    println("    l - stop Kotlin Language Server")
                }
                group.types.contains(ProcessType.KOTLIN_DAEMON) ||
                    group.types.contains(ProcessType.GRADLE_DAEMON) -> {
                    println("    g - stop Gradle Daemon")
                }
                group.types.contains(ProcessType.UNKNOWN) -> {
                    println("    o - stop Other")
                }
            }
        }

        println("")
        print("Enter command to stop process: ")

        val scanner = Scanner(System.`in`)
        val command = scanner.nextLine()

        val pids = if (command.contains("a", ignoreCase = true)) {
            groups.flatMap { group -> group.processes }
                .map { process -> process.pid }
        } else {
            val typesToKill = command.trim()
                .mapNotNull { char -> ARGUMENT_TO_PROCESS_TYPE_MAP[char] }
                .flatten()
                .toSet()

            groups.flatMap { group -> group.processes }
                .mapNotNull { process ->
                    if (typesToKill.contains(process.type)) {
                        process.pid
                    } else {
                        null
                    }
                }
        }

        return Either.Right(pids)
    }

    companion object {
        private val ARGUMENT_TO_PROCESS_TYPE_MAP = mapOf(
            's' to setOf(ProcessType.ANDROID_STUDIO),
            'i' to setOf(ProcessType.INTELLIJ_IDEA),
            'g' to setOf(ProcessType.GRADLE_DAEMON, ProcessType.KOTLIN_DAEMON),
            'l' to setOf(ProcessType.KOTLIN_LANGUAGE_SERVER),
            'o' to setOf(ProcessType.UNKNOWN)
        )
    }
}