package com.github.ai.jpkiller.domain

import com.github.ai.jpkiller.data.parser.argument.ArgumentParser
import com.github.ai.jpkiller.domain.output.OutputPrinter
import com.github.ai.jpkiller.domain.usecases.AskToKillUseCase
import com.github.ai.jpkiller.domain.usecases.GetDataUseCase
import com.github.ai.jpkiller.domain.usecases.KillProcessUseCase
import com.github.ai.jpkiller.domain.usecases.PrintHelpUseCase
import com.github.ai.jpkiller.domain.usecases.PrintMemoryUsageUseCase

class MainInteractor(
    private val printHelpUseCase: PrintHelpUseCase,
    private val getDataUseCase: GetDataUseCase,
    private val printMemoryUsageUseCase: PrintMemoryUsageUseCase,
    private val askToKillUseCase: AskToKillUseCase,
    private val killProcessUseCase: KillProcessUseCase,
    private val argumentParser: ArgumentParser,
    private val printer: OutputPrinter
) {

    fun start(commandLineArgs: Array<String>) {
        val arguments = argumentParser.parse(commandLineArgs)
        if (arguments.isLeft()) {
            println(arguments.unwrapError())
            return
        }

        val args = arguments.unwrap()
        if (args.isPrintHelp) {
            printHelpUseCase.printHelp(printer)
            return
        }

        val data = getDataUseCase.getProcessData()
        if (data.isLeft()) {
            println(data.unwrapError())
            return
        }

        printMemoryUsageUseCase.printMemoryUsage(data.unwrap(), printer)
        if (args.isInteractionDisabled) {
            return
        }

        val pids = askToKillUseCase.getPidsToKill(data.unwrap(), printer)
        if (pids.isLeft()) {
            println(pids.unwrapError())
            return
        }

        killProcessUseCase.kill(pids.unwrap(), printer)
    }
}