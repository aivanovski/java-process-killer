package com.github.ai.jpkiller.data.parser.top

import com.github.ai.jpkiller.entity.Either
import com.github.ai.jpkiller.entity.PidAndMemory

interface TopOutputParser {
    fun parse(output: String): Either<List<PidAndMemory>>
}