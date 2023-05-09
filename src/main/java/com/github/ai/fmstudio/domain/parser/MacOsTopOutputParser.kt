package com.github.ai.fmstudio.domain.parser

import com.github.ai.fmstudio.entity.Either
import com.github.ai.fmstudio.entity.PidAndMemory
import com.github.ai.fmstudio.utils.StringUtils.NEW_LINE
import com.github.ai.fmstudio.utils.StringUtils.SPACE
import com.github.ai.fmstudio.utils.getOrNull
import com.github.ai.fmstudio.utils.isNumber
import com.github.ai.fmstudio.utils.toIntSafely

class MacOsTopOutputParser(
    private val memorySizeParser: MemorySizeParser
) : TopOutputParser {

    override fun parse(output: String): Either<List<PidAndMemory>> {
        val data = output.split(NEW_LINE)
            .map { line -> line.trim() }
            .filter { line ->
                line.isNotEmpty() && line.substringBefore(SPACE).isNumber()
            }
            .mapNotNull { line ->
                val words = line.split(SPACE)
                    .map { word -> word.trim() }
                    .filter { word -> word.isNotEmpty() }

                val pid = words.getOrNull(0)?.toIntSafely()
                val memory = words.getOrNull(7)?.let {
                    memorySizeParser.parseMemorySize(it)
                }

                if (words.size >= 8 && pid != null && memory != null) {
                    PidAndMemory(pid, memory)
                } else {
                    null
                }
            }

        return Either.Right(data)
    }
}