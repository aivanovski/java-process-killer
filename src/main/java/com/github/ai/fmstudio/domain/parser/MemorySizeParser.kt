package com.github.ai.fmstudio.domain.parser

import com.github.ai.fmstudio.utils.toLongSafely

class MemorySizeParser {

    fun parseMemorySize(size: String): Long? {
        for (unit in SizeUnit.values()) {
            if (size.contains(unit.symbol, ignoreCase = true)) {
                val value = size.replace(unit.symbol, "").toLongSafely()
                    ?: return null

                return unit.factor * value
            }
        }

        return null
    }

    private enum class SizeUnit(val factor: Long, val symbol: String) {
        NONE(1, "B"),
        KILO(1024, "K"),
        MEGA(1024 * 1024, "M"),
        GIGA(1024 * 1024 * 1024, "G")
    }
}