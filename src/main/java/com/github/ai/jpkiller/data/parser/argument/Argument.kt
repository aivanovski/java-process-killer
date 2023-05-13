package com.github.ai.jpkiller.data.parser.argument

enum class Argument(
    val shortName: String,
    val fullName: String
) {
    NO_INTERACTION("n", "no-interaction"),
    HELP("h", "help");

    val cliShortName: String = "-$shortName"
    val cliFullName: String = "--$fullName"
}