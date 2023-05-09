package com.github.ai.fmstudio.domain

import com.github.ai.fmstudio.domain.usecases.AskToKillUseCase
import com.github.ai.fmstudio.domain.usecases.GetOsTypeUseCase
import com.github.ai.fmstudio.domain.usecases.GetProcessesUseCase
import com.github.ai.fmstudio.domain.usecases.GetUsedMemoryUseCase
import com.github.ai.fmstudio.domain.usecases.KillProcessUseCase
import com.github.ai.fmstudio.domain.usecases.PrintMemoryUsageUseCase
import com.github.ai.fmstudio.entity.PidAndCommand
import com.github.ai.fmstudio.entity.PidAndMemory
import com.github.ai.fmstudio.entity.PidAndType
import com.github.ai.fmstudio.entity.ProcessGroup
import com.github.ai.fmstudio.entity.ProcessInfo
import com.github.ai.fmstudio.entity.ProcessType

class MainInteractor(
    private val getProcessesUseCase: GetProcessesUseCase,
    private val getOsTypeUseCase: GetOsTypeUseCase,
    private val getUsedMemoryUseCase: GetUsedMemoryUseCase,
    private val printMemoryUsageUseCase: PrintMemoryUsageUseCase,
    private val askToKillUseCase: AskToKillUseCase,
    private val killProcessUseCase: KillProcessUseCase,
    private val classifier: ProcessClassifier
) {

    fun start() {
        val osType = getOsTypeUseCase.getOSType()
        if (osType.isLeft()) {
            println(osType.unwrapError())
            return
        }

        val commands = getProcessesUseCase.getProcesses()
        if (commands.isLeft()) {
            println(commands.unwrapError())
            return
        }

        val types = classifier.classify(osType.unwrap(), commands.unwrap())
        if (types.isEmpty()) {
            println("Not processes found")
            return
        }

        val memory = getUsedMemoryUseCase.getMemoryUsage(osType.unwrap())
        if (memory.isLeft()) {
            println(memory.unwrapError())
            return
        }

        val data = combineData(commands.unwrap(), types, memory.unwrap())
        val groups = groupProcesses(data)

        printMemoryUsageUseCase.printMemoryUsage(groups)
        val pids = askToKillUseCase.getPidsToKill(groups)
        if (pids.isLeft()) {
            println(pids.unwrapError())
            return
        }

        killProcessUseCase.kill(pids.unwrap())
    }

    private fun groupProcesses(data: List<ProcessInfo>): List<ProcessGroup> {
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

            val usedMemory = processes.sumOf { process -> process.usedMemory }

            groups.add(
                ProcessGroup(
                    name = name,
                    types = types,
                    processes = processes,
                    usedMemory = usedMemory
                )
            )
        }

        return groups.sortedByDescending { group -> group.usedMemory }
    }

    private fun combineData(
        commands: List<PidAndCommand>,
        types: List<PidAndType>,
        memory: List<PidAndMemory>
    ): List<ProcessInfo> {
        val typeMap = types.associate { (pid, type) -> Pair(pid, type) }
        val memoryMap = memory.associate { (pid, size) -> Pair(pid, size) }

        return commands
            .filter { (pid, _) -> typeMap.contains(pid) }
            .map { (pid, command) ->
                ProcessInfo(
                    pid = pid,
                    command = command,
                    usedMemory = memoryMap[pid] ?: 0L,
                    type = typeMap[pid] ?: ProcessType.UNKNOWN,
                )
            }
            .sortedByDescending { process -> process.usedMemory }
    }
}