package com.github.ai.jpkiller.di

import com.github.ai.jpkiller.data.parser.ps.DefaultPsOutputParser
import com.github.ai.jpkiller.data.parser.ps.PsOutputParser
import com.github.ai.jpkiller.data.parser.size.ByteCountParserProvider
import com.github.ai.jpkiller.data.parser.top.TopOutputParserProvider
import com.github.ai.jpkiller.domain.MainInteractor
import com.github.ai.jpkiller.domain.ProcessClassifier
import com.github.ai.jpkiller.domain.ProcessExecutor
import com.github.ai.jpkiller.domain.SystemPropertyProvider
import com.github.ai.jpkiller.domain.usecases.AskToKillUseCase
import com.github.ai.jpkiller.domain.usecases.GetOsTypeUseCase
import com.github.ai.jpkiller.domain.usecases.GetProcessesUseCase
import com.github.ai.jpkiller.domain.usecases.GetUsedMemoryUseCase
import com.github.ai.jpkiller.domain.usecases.KillProcessUseCase
import com.github.ai.jpkiller.domain.usecases.PrintMemoryUsageUseCase
import org.koin.dsl.module

object KoinModule {

    val appModule = module {
        single { ProcessExecutor() }
        single { ProcessClassifier() }
        single { SystemPropertyProvider() }
        single { ByteCountParserProvider() }
        single { TopOutputParserProvider(get()) }
        single<PsOutputParser> { DefaultPsOutputParser() }

        // Use-Cases
        single { GetOsTypeUseCase(get()) }
        single { GetProcessesUseCase(get(), get()) }
        single { GetUsedMemoryUseCase(get(), get()) }
        single { PrintMemoryUsageUseCase() }
        single { AskToKillUseCase(get()) }
        single { KillProcessUseCase(get()) }

        // Interactors
        single { MainInteractor(get(), get(), get(), get(), get(), get(), get()) }
    }
}