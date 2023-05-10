package com.github.ai.jpkiller.domain

import com.github.ai.jpkiller.entity.OSType
import com.github.ai.jpkiller.entity.PidAndCommand
import com.github.ai.jpkiller.entity.PidAndType
import com.github.ai.jpkiller.entity.ProcessType

class ProcessClassifier {

    fun classify(
        osType: OSType,
        processes: List<PidAndCommand>
    ): List<PidAndType> {
        return processes
            .filter { (_, command) -> isNotJpkillerItself(command) && isClassifiable(command) }
            .map { (pid, command) ->
                PidAndType(
                    pid = pid,
                    type = determineProcessType(osType, command)
                )
            }
    }

    private fun determineProcessType(osType: OSType, command: String): ProcessType {
        return when {
            isAndroidStudio(command) -> ProcessType.ANDROID_STUDIO
            isIntellijIdea(command) -> ProcessType.INTELLIJ_IDEA
            isKotlinDaemon(command) -> ProcessType.KOTLIN_DAEMON
            isGradleDaemon(command) -> ProcessType.GRADLE_DAEMON
            isKotlinLanguageServer(command) -> ProcessType.KOTLIN_LANGUAGE_SERVER
            else -> ProcessType.UNKNOWN
        }
    }

    private fun isNotJpkillerItself(command: String): Boolean {
        return !command.contains("jpkiller")
    }

    private fun isClassifiable(command: String): Boolean {
        if (command.contains("java", ignoreCase = true)) {
            return true
        }

        return !command.contains("fsnotifier") &&
            (command.contains("idea") || command.contains("studio"))
    }

    private fun isAndroidStudio(command: String): Boolean {
        if (command.contains("AndroidStudio")) {
            return true
        }

        return command.contains("studio") &&
            !command.contains("-javaagent:") &&
            !command.contains("GradleDaemon") &&
            !command.contains("studio.sh")
    }

    private fun isIntellijIdea(command: String): Boolean {
        return (command.contains("idea") && !command.contains("-javaagent:")) ||
            (command.contains("idea_rt.jar") && !command.contains("-javaagent:"))
    }

    private fun isKotlinDaemon(command: String): Boolean {
        return command.contains("kotlin-daemon")
    }

    private fun isGradleDaemon(command: String): Boolean {
        return command.contains("GradleDaemon")
    }

    private fun isKotlinLanguageServer(command: String): Boolean {
        return command.contains("kotlinLanguageServer")
    }
}