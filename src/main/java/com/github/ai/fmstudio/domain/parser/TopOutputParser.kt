package com.github.ai.fmstudio.domain.parser

import com.github.ai.fmstudio.entity.Either
import com.github.ai.fmstudio.entity.PidAndMemory

interface TopOutputParser {
    fun parse(output: String): Either<List<PidAndMemory>>
}