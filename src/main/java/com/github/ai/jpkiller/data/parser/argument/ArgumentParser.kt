package com.github.ai.jpkiller.data.parser.argument

import com.github.ai.jpkiller.domain.Strings
import com.github.ai.jpkiller.entity.Arguments
import com.github.ai.jpkiller.entity.Either
import com.github.ai.jpkiller.entity.exception.ParsingException
import java.util.LinkedList

class ArgumentParser {

    fun parse(args: Array<String>): Either<Arguments> {
        val queue = LinkedList(args.toList())

        var isInteractionDisabled = false
        var isPrintHelp = false

        while (queue.isNotEmpty()) {
            val arg = queue.poll()

            val type = ARGUMENT_MAP[arg]
                ?: return Either.Left(
                    ParsingException(
                        String.format(
                            Strings.errorUnknownArgument,
                            arg
                        )
                    )
                )

            when (type) {
                Argument.HELP -> {
                    isPrintHelp = true
                }
                Argument.NO_INTERACTION -> {
                    isInteractionDisabled = true
                }
            }
        }

        return Either.Right(
            Arguments(
                isInteractionDisabled = isInteractionDisabled,
                isPrintHelp = isPrintHelp
            )
        )
    }

    companion object {
        private val ARGUMENT_MAP = mapOf(
            Argument.HELP.cliFullName to Argument.HELP,
            Argument.HELP.cliShortName to Argument.HELP,

            Argument.NO_INTERACTION.cliFullName to Argument.NO_INTERACTION,
            Argument.NO_INTERACTION.cliShortName to Argument.NO_INTERACTION
        )
    }
}