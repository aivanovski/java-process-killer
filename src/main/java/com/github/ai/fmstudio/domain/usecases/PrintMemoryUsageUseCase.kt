package com.github.ai.fmstudio.domain.usecases

import com.github.ai.fmstudio.entity.ProcessGroup
import com.github.ai.fmstudio.utils.StringUtils.formatSizeInBytes

class PrintMemoryUsageUseCase {

    fun printMemoryUsage(groups: List<ProcessGroup>) {
        println("Used Memory:")

        for (group in groups) {
            val size = formatSizeInBytes(group.usedMemory)

            println("    by ${group.name}: $size in ${group.processes.size} process(es)")
        }
    }
}