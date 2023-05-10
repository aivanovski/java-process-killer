package com.github.ai.jpkiller.data.parser.size

import com.github.ai.jpkiller.entity.OSType

class ByteCountParserProvider {

    fun getParser(osType: OSType): ByteCountParser {
        return when (osType) {
            OSType.LINUX -> LinuxByteCountParser()
            OSType.MAC_OS -> MacOsByteCountParser()
        }
    }
}