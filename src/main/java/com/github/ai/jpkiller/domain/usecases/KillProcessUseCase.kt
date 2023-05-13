package com.github.ai.jpkiller.domain.usecases

import com.github.ai.jpkiller.domain.ProcessExecutor
import com.github.ai.jpkiller.domain.output.OutputPrinter
import com.github.ai.jpkiller.entity.Either

class KillProcessUseCase(
    private val processExecutor: ProcessExecutor
) {

    fun kill(
        pids: List<Int>,
        printer: OutputPrinter
    ): Either<Unit> {
        if (pids.isEmpty()) {
            printer.println("No processes where specified")
            return Either.Right(Unit)
        }

        for (pid in pids) {
            printer.println("Killing process with pid: $pid")
            val result = processExecutor.execute("kill -9 $pid")
            if (result.isLeft()) {
                return result.mapToLeft()
            }
        }

        return Either.Right(Unit)
    }
}