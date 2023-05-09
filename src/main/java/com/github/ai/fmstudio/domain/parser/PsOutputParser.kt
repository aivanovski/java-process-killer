package com.github.ai.fmstudio.domain.parser

import com.github.ai.fmstudio.entity.Either
import com.github.ai.fmstudio.entity.PidAndCommand

interface PsOutputParser {

    fun parse(output: String): Either<List<PidAndCommand>>
}