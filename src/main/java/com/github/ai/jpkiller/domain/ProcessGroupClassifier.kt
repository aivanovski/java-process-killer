package com.github.ai.jpkiller.domain

import com.github.ai.jpkiller.entity.ProcessGroup
import com.github.ai.jpkiller.entity.ProcessGroupType
import com.github.ai.jpkiller.entity.ProcessInfo
import com.github.ai.jpkiller.entity.ProcessType

class ProcessGroupClassifier {

    fun classify(
        data: List<ProcessInfo>
    ): List<ProcessGroup> {
        val namesAndTypes = listOf(
            ProcessGroupType.IDE to setOf(ProcessType.ANDROID_STUDIO, ProcessType.INTELLIJ_IDEA),
            ProcessGroupType.KOTLIN_LANGUAGE_SERVER to setOf(ProcessType.KOTLIN_LANGUAGE_SERVER),
            ProcessGroupType.GRADLE to setOf(ProcessType.GRADLE_DAEMON, ProcessType.KOTLIN_DAEMON),
            ProcessGroupType.UNCLASSIFIED to setOf(ProcessType.UNKNOWN)
        )

        val groups = mutableListOf<ProcessGroup>()

        for ((groupType, types) in namesAndTypes) {
            val processes = data.filter { process -> types.contains(process.type) }
            if (processes.isEmpty()) {
                continue
            }

            val usedMemory = processes.map { process -> process.usedMemory }
                .reduce { acc, next -> acc + next }

            groups.add(
                ProcessGroup(
                    type = groupType,
                    types = types,
                    processes = processes,
                    usedMemory = usedMemory
                )
            )
        }

        return groups.sortedByDescending { group -> group.usedMemory.bytes }
    }
}