package com.github.ai.jpkiller.data.parser.ps

import com.github.ai.jpkiller.entity.Either
import com.github.ai.jpkiller.entity.PidAndCommand

interface PsOutputParser {

    fun parse(output: String): Either<List<PidAndCommand>>
}