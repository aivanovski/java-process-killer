package com.github.ai.jpkiller.domain.usecases

import com.github.ai.jpkiller.data.parser.ps.PsOutputParser
import com.github.ai.jpkiller.domain.ProcessExecutor
import com.github.ai.jpkiller.entity.Either
import com.github.ai.jpkiller.entity.PidAndCommand

class GetProcessesUseCase(
    private val processExecutor: ProcessExecutor,
    private val parser: PsOutputParser
) {

    fun getProcesses(): Either<List<PidAndCommand>> {
        val output = processExecutor.execute("ps -u \"\$(id -u)\" -o pid,command")
        if (output.isLeft()) {
            return output.mapToLeft()
        }

        val processes = parser.parse(output.unwrap())
        if (processes.isLeft()) {
            return processes.mapToLeft()
        }

        return Either.Right(processes.unwrap())
    }
}