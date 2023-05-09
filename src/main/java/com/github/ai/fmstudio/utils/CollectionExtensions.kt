package com.github.ai.fmstudio.utils

fun <T> List<T>.getOrNull(index: Int): T? {
    return if (this.size > index) {
        this[index]
    } else {
        null
    }
}