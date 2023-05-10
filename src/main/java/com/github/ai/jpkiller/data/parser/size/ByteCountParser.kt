package com.github.ai.jpkiller.data.parser.size

import com.github.ai.jpkiller.entity.ByteCount

interface ByteCountParser {
    fun parse(bytes: String): ByteCount?
}