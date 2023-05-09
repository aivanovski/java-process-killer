package com.github.ai.jpk.domain.parser

import com.github.ai.jpk.entity.Either
import com.github.ai.jpk.entity.PidAndMemory

interface TopOutputParser {
    fun parse(output: String): Either<List<PidAndMemory>>
}