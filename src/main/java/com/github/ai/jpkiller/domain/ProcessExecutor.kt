package com.github.ai.jpkiller.domain

import com.github.ai.jpkiller.entity.Either
import org.buildobjects.process.ProcBuilder

class ProcessExecutor {

    fun execute(command: String): Either<String> {
        return try {
            val result = ProcBuilder("/bin/sh", "-c", command)
                .run()
                .outputString

            Either.Right(result)
        } catch (exception: Exception) {
            Either.Left(exception)
        }
    }
}