package com.github.ai.jpk.domain.usecases

import com.github.ai.jpk.domain.ProcessExecutor
import com.github.ai.jpk.entity.Either

class KillProcessUseCase(
    private val processExecutor: ProcessExecutor
) {

    fun kill(pids: List<Int>): Either<Unit> {
        for (pid in pids) {
            println("Killing process with pid: $pid")
            val result = processExecutor.execute("kill -9 $pid")
            if (result.isLeft()) {
                return result.mapToLeft()
            }
        }

        return Either.Right(Unit)
    }
}