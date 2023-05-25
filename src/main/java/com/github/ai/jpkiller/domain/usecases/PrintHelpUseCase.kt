package com.github.ai.jpkiller.domain.usecases

import com.github.ai.jpkiller.domain.output.OutputPrinter

class PrintHelpUseCase(
    private val getVersionUseCase: GetVersionUseCase
) {

    fun printHelp(printer: OutputPrinter) {
        printer.println(
            String.format(
                HELP_TEXT,
                getVersionUseCase.getVersionName()
            )
        )
    }

    companion object {
        internal val HELP_TEXT = """
            jpkiller (java-process-killer) %s
            Finds and stops java processes

            USAGE:
                jpkiller [OPTIONS]

            OPTIONS:
                -n, --no-interaction             Do not ask to stop processes, just prints information about them
                -h, --help                       Print help information
        """.trimIndent()
    }
}