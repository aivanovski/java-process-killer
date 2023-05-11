package com.github.ai.jpkiller.domain.usecases

import com.github.ai.jpkiller.domain.ProcessClassifier
import com.github.ai.jpkiller.domain.ProcessGroupClassifier
import com.github.ai.jpkiller.entity.ByteCount
import com.github.ai.jpkiller.entity.Either
import com.github.ai.jpkiller.entity.PidAndCommand
import com.github.ai.jpkiller.entity.PidAndMemory
import com.github.ai.jpkiller.entity.PidAndType
import com.github.ai.jpkiller.entity.ProcessGroup
import com.github.ai.jpkiller.entity.ProcessInfo
import com.github.ai.jpkiller.entity.ProcessType

class GetDataUseCase(
    private val getProcessesUseCase: GetProcessesUseCase,
    private val getOsTypeUseCase: GetOsTypeUseCase,
    private val getUsedMemoryUseCase: GetUsedMemoryUseCase,
    private val processClassifier: ProcessClassifier,
    private val groupClassifier: ProcessGroupClassifier
) {

    fun getProcessData(): Either<List<ProcessGroup>> {
        val osType = getOsTypeUseCase.getOSType()
        if (osType.isLeft()) {
            return osType.mapToLeft()
        }

        val processes = getProcessesUseCase.getProcesses()
        if (processes.isLeft()) {
            return processes.mapToLeft()
        }

        val types = processClassifier.classify(osType.unwrap(), processes.unwrap())
        if (types.isEmpty()) {
            return Either.Right(emptyList())
        }

        val memoryData = getUsedMemoryUseCase.getMemoryUsage(osType.unwrap())
        if (memoryData.isLeft()) {
            return memoryData.mapToLeft()
        }

        val data = combineData(processes.unwrap(), types, memoryData.unwrap())
        val groups = groupClassifier.classify(data)

        return Either.Right(groups)
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
                    usedMemory = memoryMap[pid] ?: ByteCount.Number(0),
                    type = typeMap[pid] ?: ProcessType.UNKNOWN
                )
            }
            .sortedByDescending { process -> process.usedMemory.bytes }
    }
}