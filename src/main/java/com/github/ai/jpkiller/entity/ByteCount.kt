package com.github.ai.jpkiller.entity

import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.min
import kotlin.math.pow

sealed class ByteCount(val bytes: Long) {

    operator fun plus(other: ByteCount): ByteCount {
        return Number(bytes + other.bytes)
    }

    fun format(): String {
        return when (this) {
            is Decimal -> {
                "$value ${unitFactor.shortName}"
            }
            is Number -> formatSizeInBytes(value)
        }
    }

    private fun formatSizeInBytes(sizeInBytes: Long): String {
        if (sizeInBytes <= 0) {
            return "0 B"
        }

        val digitGroups = (log10(sizeInBytes.toDouble()) / log10(1024.0)).toInt()
        val factor = UnitFactor.values()[min(digitGroups, UnitFactor.values().lastIndex)]

        return DecimalFormat("#,##0.#")
            .format(sizeInBytes / 1024.0.pow(digitGroups.toDouble()))
            .toString() + " " + factor.shortName
    }

    data class Decimal(
        val value: Double,
        val unitFactor: UnitFactor
    ) : ByteCount((value * unitFactor.factor).toLong()) {

        override fun toString(): String {
            return format()
        }
    }

    data class Number(val value: Long) : ByteCount(value) {
        override fun toString(): String {
            return format()
        }
    }

    enum class UnitFactor(
        val factor: Long,
        val shortName: String
    ) {
        BYTES(1L, "B"),
        KILO_BYTES(KILO, "KB"),
        MEGA_BYTES(MEGA, "MB"),
        GIGA_BYTES(GIGA, "GB"),
        TERA_BYTES(TERA, "TB")
    }

    companion object {
        private const val KILO = 1024L
        private const val MEGA = KILO * KILO
        private const val GIGA = KILO * MEGA
        private const val TERA = KILO * GIGA
    }
}