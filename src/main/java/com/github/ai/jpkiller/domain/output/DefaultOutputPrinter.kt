package com.github.ai.jpkiller.domain.output

class DefaultOutputPrinter : OutputPrinter {

    override fun println(line: String) {
        kotlin.io.println(line)
    }

    override fun print(line: String) {
        kotlin.io.print(line)
    }

    override fun printStackTrace(exception: Exception) {
        exception.printStackTrace()
    }
}