package com.github.ai.fmstudio.utils

import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.min
import kotlin.math.pow

object StringUtils {
    const val EMPTY = ""
    const val SPACE = " "
    const val NEW_LINE = "\n"

    private val UNITS = listOf("B", "KB", "MB", "GB", "TB")

    fun formatSizeInBytes(sizeInBytes: Long): String {
        if (sizeInBytes <= 0) {
            return "0 B"
        }

        val digitGroups = (log10(sizeInBytes.toDouble()) / log10(1024.0)).toInt()

        return DecimalFormat("#,##0.#")
            .format(sizeInBytes / 1024.0.pow(digitGroups.toDouble()))
            .toString() + " " + UNITS[min(digitGroups, UNITS.lastIndex)]
    }
}