package com.github.ai.fmstudio.domain.parser

import com.github.ai.fmstudio.entity.Either
import com.github.ai.fmstudio.entity.PidAndCommand
import com.github.ai.fmstudio.utils.StringUtils.NEW_LINE
import com.github.ai.fmstudio.utils.StringUtils.SPACE
import com.github.ai.fmstudio.utils.isNumber
import com.github.ai.fmstudio.utils.toIntSafely

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