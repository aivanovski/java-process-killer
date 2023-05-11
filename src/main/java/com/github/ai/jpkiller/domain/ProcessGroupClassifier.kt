package com.github.ai.jpkiller.domain

import com.github.ai.jpkiller.entity.ProcessGroup
import com.github.ai.jpkiller.entity.ProcessInfo
import com.github.ai.jpkiller.entity.ProcessType

class ProcessGroupClassifier {

    fun classify(
        data: List<ProcessInfo>
    ): List<ProcessGroup> {
        val namesAndTypes = listOf(
            "Android Studio" to setOf(ProcessType.ANDROID_STUDIO),
            "Intellij Idea" to setOf(ProcessType.INTELLIJ_IDEA),
            "Kotlin Language Server" to setOf(ProcessType.KOTLIN_LANGUAGE_SERVER),
            "Gradle Daemon" to setOf(ProcessType.GRADLE_DAEMON, ProcessType.KOTLIN_DAEMON),
            "Other" to setOf(ProcessType.UNKNOWN)
        )

        val groups = mutableListOf<ProcessGroup>()

        for ((name, types) in namesAndTypes) {
            val processes = data.filter { process -> types.contains(process.type) }
            if (processes.isEmpty()) {
                continue
            }

            val usedMemory = processes.map { process -> process.usedMemory }
                .reduce { acc, next -> acc + next }

            groups.add(
                ProcessGroup(
                    name = name,
                    types = types,
                    processes = processes,
                    usedMemory = usedMemory
                )
            )
        }

        return groups.sortedByDescending { group -> group.usedMemory.bytes }
    }
}