package com.github.ai.jpkiller.data.parser.size

import com.github.ai.jpkiller.entity.ByteCount
import com.github.ai.jpkiller.utils.StringUtils
import com.github.ai.jpkiller.utils.isDecimal
import com.github.ai.jpkiller.utils.isNumber

class LinuxByteCountParser : ByteCountParser {

    override fun parse(bytes: String): ByteCount? {
        if (bytes.isNumber()) {
            return ByteCount.Number(bytes.toLong() * 1024L)
        }

        for ((symbol, unit) in UNITS.entries) {
            if (bytes.contains(symbol, ignoreCase = true)) {
                val cleaned = bytes.replace(symbol, StringUtils.EMPTY, ignoreCase = true)

                return when {
                    cleaned.isDecimal() -> {
                        ByteCount.Decimal(
                            value = cleaned.toDouble(),
                            unitFactor = unit
                        )
                    }
                    cleaned.isNumber() -> {
                        ByteCount.Number(
                            value = cleaned.toLong() * unit.factor
                        )
                    }
                    else -> null
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