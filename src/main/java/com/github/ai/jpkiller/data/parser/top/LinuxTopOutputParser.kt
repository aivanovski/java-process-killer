package com.github.ai.jpkiller.data.parser.top

import com.github.ai.jpkiller.data.parser.size.ByteCountParser
import com.github.ai.jpkiller.entity.Either
import com.github.ai.jpkiller.entity.PidAndMemory
import com.github.ai.jpkiller.utils.StringUtils
import com.github.ai.jpkiller.utils.getOrNull
import com.github.ai.jpkiller.utils.isNumber
import com.github.ai.jpkiller.utils.toIntSafely

class LinuxTopOutputParser(
    private val byteCountParser: ByteCountParser
) : TopOutputParser {

    override fun parse(output: String): Either<List<PidAndMemory>> {
        val data = output.split(StringUtils.NEW_LINE)
            .map { line -> line.trim() }
            .filter { line ->
                line.isNotEmpty() && line.substringBefore(StringUtils.SPACE).isNumber()
            }
            .mapNotNull { line ->
                val words = line.split(StringUtils.SPACE)
                    .map { word -> word.trim() }
                    .filter { word -> word.isNotEmpty() }

                val pid = words.getOrNull(0)?.toIntSafely()
                val memory = words.getOrNull(5)?.let {
                    byteCountParser.parse(it)
                }

                if (pid != null && memory != null) {
                    PidAndMemory(pid, memory)
                } else {
                    null
                }
            }

        return Either.Right(data)
    }
}