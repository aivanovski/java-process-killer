package com.github.ai.jpkiller.domain.usecases

import com.github.ai.jpkiller.entity.ProcessGroup

class PrintMemoryUsageUseCase {

    fun printMemoryUsage(groups: List<ProcessGroup>) {
        println("Used Memory:")

        for (group in groups) {
            println(
                String.format(
                    "    %s: %s in %s process(es)",
                    group.name,
                    group.usedMemory.format(),
                    group.processes.size
                )
            )
        }
    }
}