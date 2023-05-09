package com.github.ai.jpk.domain.parser

import com.github.ai.jpk.entity.Either
import com.github.ai.jpk.entity.PidAndCommand

interface PsOutputParser {

    fun parse(output: String): Either<List<PidAndCommand>>
}