package com.github.ai.fmstudio.di

import com.github.ai.fmstudio.domain.MainInteractor
import com.github.ai.fmstudio.domain.ProcessClassifier
import com.github.ai.fmstudio.domain.ProcessExecutor
import com.github.ai.fmstudio.domain.SystemPropertyProvider
import com.github.ai.fmstudio.domain.parser.DefaultPsOutputParser
import com.github.ai.fmstudio.domain.parser.MemorySizeParser
import com.github.ai.fmstudio.domain.parser.PsOutputParser
import com.github.ai.fmstudio.domain.parser.TopOutputParserProvider
import com.github.ai.fmstudio.domain.usecases.AskToKillUseCase
import com.github.ai.fmstudio.domain.usecases.GetOsTypeUseCase
import com.github.ai.fmstudio.domain.usecases.GetProcessesUseCase
import com.github.ai.fmstudio.domain.usecases.GetUsedMemoryUseCase
import com.github.ai.fmstudio.domain.usecases.KillProcessUseCase
import com.github.ai.fmstudio.domain.usecases.PrintMemoryUsageUseCase
import org.koin.dsl.module

object KoinModule {

    val appModule = module {
        single { ProcessExecutor() }
        single { ProcessClassifier() }
        single { SystemPropertyProvider() }
        single { MemorySizeParser() }
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