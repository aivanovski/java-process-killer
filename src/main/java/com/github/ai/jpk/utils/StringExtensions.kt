package com.github.ai.jpk.utils

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

fun String.trimIfLongerThan(maxLength: Int): String {
    return if (this.length > maxLength) {
        this.substring(0, maxLength) + "..."
    } else {
        this
    }
}