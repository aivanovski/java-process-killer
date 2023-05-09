package com.github.ai.jpk.utils

fun <T> List<T>.getOrNull(index: Int): T? {
    return if (this.size > index) {
        this[index]
    } else {
        null
    }
}