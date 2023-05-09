package com.github.ai.fmstudio.domain.parser

import com.github.ai.fmstudio.entity.OSType

class TopOutputParserProvider(
    private val memorySizeParser: MemorySizeParser
) {

    fun getParser(osType: OSType): TopOutputParser {
        return when (osType) {
            OSType.LINUX -> TODO()
            OSType.MAC_OS -> MacOsTopOutputParser(memorySizeParser)
        }
    }
}