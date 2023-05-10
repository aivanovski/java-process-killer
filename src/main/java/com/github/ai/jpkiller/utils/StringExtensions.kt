package com.github.ai.jpkiller.utils

fun String.toIntSafely(): Int? {
    if (!this.isNumber()) {
        return null
    }

    return try {
        this.toInt()
    } catch (e: Exception) {
        null
    }
}

fun String.toLongSafely(): Long? {
    if (!this.isNumber()) {
        return null
    }

    return try {
        this.toLong()
    } catch (e: Exception) {
        null
    }
}

fun String.isNumber(): Boolean {
    if (this.isEmpty()) {
        return false
    }

    return all { char -> char.isDigit() }
}

fun String.isDecimal(): Boolean {
    if (this.isEmpty()) {
        return false
    }

    val dotCount = this.count { char -> char == '.' }

    return this
        .filter { char -> char != '.' }
        .all { char -> char.isDigit() && dotCount == 1 }
}

fun String.trimIfLongerThan(maxLength: Int): String {
    return if (this.length > maxLength) {
        this.substring(0, maxLength) + "..."
    } else {
        this
    }
}