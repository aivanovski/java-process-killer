package com.github.ai.jpk.domain.usecases

import com.github.ai.jpk.domain.ProcessExecutor
import com.github.ai.jpk.domain.parser.TopOutputParserProvider
import com.github.ai.jpk.entity.Either
import com.github.ai.jpk.entity.OSType
import com.github.ai.jpk.entity.PidAndMemory

class GetUsedMemoryUseCase(
    private val processExecutor: ProcessExecutor,
    private val parserProvider: TopOutputParserProvider
) {

    fun getMemoryUsage(osType: OSType): Either<List<PidAndMemory>> {
        val command = when (osType) {
            OSType.LINUX -> "top -n 1 -b -o %MEM"
            OSType.MAC_OS -> "top -l 1 -o mem"
        }

        val output = processExecutor.execute(command)
        if (output.isLeft()) {
            return output.mapToLeft()
        }

        val data = parserProvider.getParser(osType).parse(output.unwrap())
        if (data.isLeft()) {
            return data.mapToLeft()
        }

        return Either.Right(data.unwrap())
    }
}