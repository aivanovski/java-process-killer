package com.github.ai.jpkiller.domain

import com.github.ai.jpkiller.domain.usecases.AskToKillUseCase
import com.github.ai.jpkiller.domain.usecases.GetDataUseCase
import com.github.ai.jpkiller.domain.usecases.KillProcessUseCase
import com.github.ai.jpkiller.domain.usecases.PrintMemoryUsageUseCase

class MainInteractor(
    private val getDataUseCase: GetDataUseCase,
    private val printMemoryUsageUseCase: PrintMemoryUsageUseCase,
    private val askToKillUseCase: AskToKillUseCase,
    private val killProcessUseCase: KillProcessUseCase,
) {

    fun start() {
        val data = getDataUseCase.getProcessData()
        if (data.isLeft()) {
            println(data.unwrapError())
            return
        }

        printMemoryUsageUseCase.printMemoryUsage(data.unwrap())
        val pids = askToKillUseCase.getPidsToKill(data.unwrap())
        if (pids.isLeft()) {
            println(pids.unwrapError())
            return
        }

        killProcessUseCase.kill(pids.unwrap())
    }
}