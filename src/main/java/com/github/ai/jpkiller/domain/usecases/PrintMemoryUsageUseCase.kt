package com.github.ai.jpkiller.domain.usecases

import com.github.ai.jpkiller.entity.ProcessGroup
import com.github.ai.jpkiller.entity.ProcessGroupType
import com.github.ai.jpkiller.entity.ProcessType

class PrintMemoryUsageUseCase {

    fun printMemoryUsage(groups: List<ProcessGroup>) {
        val ideGroups = groups.filter { group -> group.type == ProcessGroupType.IDE }
        val otherGroups = groups.filter { group -> group.type != ProcessGroupType.IDE }

        if (otherGroups.isNotEmpty()) {
            println("Used Memory:")

            for (group in otherGroups) {
                println(
                    String.format(
                        "    %s: %s in %s process(es)",
                        group.type.getTitle(),
                        group.usedMemory.format(),
                        group.processes.size
                    )
                )
            }
        }

        if (otherGroups.isNotEmpty() && ideGroups.isNotEmpty()) {
            println("")
        }

        if (ideGroups.isNotEmpty()) {
            println("Used Memory by IDE:")

            val processes = ideGroups.flatMap { group -> group.processes }
            for (process in processes) {
                println(
                    String.format(
                        "    %s: %s",
                        process.type.getTitle(),
                        process.usedMemory.format(),
                    )
                )
            }
        }
    }

    private fun ProcessGroupType.getTitle(): String {
        return when (this) {
            ProcessGroupType.IDE -> "IDE"
            ProcessGroupType.GRADLE -> "Gradle"
            ProcessGroupType.KOTLIN_LANGUAGE_SERVER -> "Kotlin Language Server"
            ProcessGroupType.UNCLASSIFIED -> "Other"
        }
    }

    private fun ProcessType.getTitle(): String {
        return when (this) {
            ProcessType.ANDROID_STUDIO -> "Android Studio"
            ProcessType.INTELLIJ_IDEA -> "IntelliJ IDEA"
            ProcessType.KOTLIN_LANGUAGE_SERVER -> "Kotlin Language Server"
            ProcessType.GRADLE_DAEMON -> "Gradle Daemon"
            ProcessType.KOTLIN_DAEMON -> "Kotlin Daemon"
            ProcessType.UNKNOWN -> "Unknown"
        }
    }
}