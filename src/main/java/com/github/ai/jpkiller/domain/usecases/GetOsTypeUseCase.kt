package com.github.ai.jpkiller.domain.usecases

import com.github.ai.jpkiller.domain.SystemPropertyProvider
import com.github.ai.jpkiller.entity.Either
import com.github.ai.jpkiller.entity.OSType

class GetOsTypeUseCase(
    private val propertyProvider: SystemPropertyProvider
) {

    fun getOSType(): Either<OSType> {
        val osName = propertyProvider.getSystemProperty(PROPERTY_OS_NAME)

        return when {
            osName.contains("Linux", ignoreCase = true) -> Either.Right(OSType.LINUX)
            osName.contains("Mac", ignoreCase = true) -> Either.Right(OSType.MAC_OS)
            else -> Either.Left(Exception("Unable to determine OS type"))
        }
    }

    companion object {
        internal const val PROPERTY_OS_NAME = "os.name"
    }
}