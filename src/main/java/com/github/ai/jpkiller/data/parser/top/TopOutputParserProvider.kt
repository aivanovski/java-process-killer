package com.github.ai.jpkiller.data.parser.top

import com.github.ai.jpkiller.data.parser.size.ByteCountParserProvider
import com.github.ai.jpkiller.entity.OSType

class TopOutputParserProvider(
    private val byteCountParserProvider: ByteCountParserProvider
) {

    fun getParser(osType: OSType): TopOutputParser {
        val byteCountParser = byteCountParserProvider.getParser(osType)

        return when (osType) {
            OSType.LINUX -> LinuxTopOutputParser(byteCountParser)
            OSType.MAC_OS -> MacOsTopOutputParser(byteCountParser)
        }
    }
}