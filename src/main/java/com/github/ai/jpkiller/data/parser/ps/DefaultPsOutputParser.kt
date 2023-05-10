package com.github.ai.jpkiller.data.parser.ps

import com.github.ai.jpkiller.entity.Either
import com.github.ai.jpkiller.entity.PidAndCommand
import com.github.ai.jpkiller.utils.StringUtils.NEW_LINE
import com.github.ai.jpkiller.utils.StringUtils.SPACE
import com.github.ai.jpkiller.utils.isNumber
import com.github.ai.jpkiller.utils.toIntSafely

class DefaultPsOutputParser : PsOutputParser {

    override fun parse(output: String): Either<List<PidAndCommand>> {
        val data = output.split(NEW_LINE)
            .map { line -> line.trim() }
            .filter { line -> line.isNotEmpty() && line.substringBefore(SPACE).isNumber() }
            .mapNotNull { line ->
                val pid = line.substringBefore(SPACE).toIntSafely()
                val command = line.substringAfter(SPACE).trim()

                if (pid != null && command.isNotEmpty()) {
                    PidAndCommand(pid, command)
                } else {
                    null
                }
            }

        return Either.Right(data)
    }
}