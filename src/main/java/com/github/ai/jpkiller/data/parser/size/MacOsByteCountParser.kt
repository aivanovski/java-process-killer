package com.github.ai.jpkiller.data.parser.size

import com.github.ai.jpkiller.entity.ByteCount
import com.github.ai.jpkiller.utils.StringUtils.EMPTY
import com.github.ai.jpkiller.utils.isNumber

class MacOsByteCountParser : ByteCountParser {

    override fun parse(bytes: String): ByteCount? {
        if (bytes.isNumber()) {
            return ByteCount.Number(bytes.toLong())
        }

        for ((symbol, unit) in UNITS.entries) {
            if (bytes.contains(symbol, ignoreCase = true)) {
                val cleaned = bytes.replace(symbol, EMPTY, ignoreCase = true)

                return if (cleaned.isNumber()) {
                    ByteCount.Number(
                        value = cleaned.toLong() * unit.factor
                    )
                } else {
                    null
                }
            }
        }

        return null
    }

    companion object {
        private val UNITS = mapOf(
            "B" to ByteCount.UnitFactor.BYTES,
            "K" to ByteCount.UnitFactor.KILO_BYTES,
            "M" to ByteCount.UnitFactor.MEGA_BYTES,
            "G" to ByteCount.UnitFactor.GIGA_BYTES,
            "T" to ByteCount.UnitFactor.TERA_BYTES
        )
    }
}