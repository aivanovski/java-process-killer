package com.github.ai.jpk.domain

import com.github.ai.jpk.entity.OSType
import com.github.ai.jpk.entity.PidAndCommand
import com.github.ai.jpk.entity.PidAndType
import com.github.ai.jpk.entity.ProcessType

class ProcessClassifier {

    fun classify(
        osType: OSType,
        processes: List<PidAndCommand>
    ): List<PidAndType> {
        return processes
            .filter { (_, command) ->
                command.contains("java", ignoreCase = true) ||
                    command.contains("idea") ||
                    command.contains("studio")
            }
            .map { (pid, command) ->
                PidAndType(
                    pid = pid,
                    type = determineProcessType(osType, command)
                )
            }
    }

    private fun determineProcessType(osType: OSType, command: String): ProcessType {
        return when {
            command.contains("studio") && !command.contains("-javaagent:")-> {
                ProcessType.ANDROID_STUDIO
            }
            command.contains("AndroidStudio") -> {
                ProcessType.ANDROID_STUDIO
            }
            command.contains("idea") && !command.contains("-javaagent:") -> {
                ProcessType.INTELLIJ_IDEA
            }
            command.contains("idea_rt.jar") && !command.contains("-javaagent:") -> {
                ProcessType.INTELLIJ_IDEA
            }
            command.contains("kotlin-daemon") -> {
                ProcessType.KOTLIN_DAEMON
            }
            command.contains("GradleDaemon") -> {
                ProcessType.GRADLE_DAEMON
            }
            command.contains("kotlinLanguageServer", ignoreCase = true) -> {
                ProcessType.KOTLIN_LANGUAGE_SERVER
            }
            else -> ProcessType.UNKNOWN
        }
    }
}