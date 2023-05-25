package com.github.ai.jpkiller.domain.output

interface OutputPrinter {
    fun println(line: String)
    fun print(line: String)
    fun printStackTrace(exception: Exception)
}