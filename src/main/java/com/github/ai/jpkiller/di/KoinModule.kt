package com.github.ai.jpkiller.di

import com.github.ai.jpkiller.data.parser.argument.ArgumentParser
import com.github.ai.jpkiller.data.parser.ps.DefaultPsOutputParser
import com.github.ai.jpkiller.data.parser.ps.PsOutputParser
import com.github.ai.jpkiller.data.parser.size.ByteCountParserProvider
import com.github.ai.jpkiller.data.parser.top.TopOutputParserProvider
import com.github.ai.jpkiller.domain.MainInteractor
import com.github.ai.jpkiller.domain.ProcessClassifier
import com.github.ai.jpkiller.domain.ProcessExecutor
import com.github.ai.jpkiller.domain.ProcessGroupClassifier
import com.github.ai.jpkiller.domain.SystemPropertyProvider
import com.github.ai.jpkiller.domain.output.DefaultOutputPrinter
import com.github.ai.jpkiller.domain.output.OutputPrinter
import com.github.ai.jpkiller.domain.usecases.AskToKillUseCase
import com.github.ai.jpkiller.domain.usecases.GetDataUseCase
import com.github.ai.jpkiller.domain.usecases.GetOsTypeUseCase
import com.github.ai.jpkiller.domain.usecases.GetProcessesUseCase
import com.github.ai.jpkiller.domain.usecases.GetUsedMemoryUseCase
import com.github.ai.jpkiller.domain.usecases.GetVersionUseCase
import com.github.ai.jpkiller.domain.usecases.KillProcessUseCase
import com.github.ai.jpkiller.domain.usecases.PrintHelpUseCase
import com.github.ai.jpkiller.domain.usecases.PrintMemoryUsageUseCase
import org.koin.dsl.module

object KoinModule {

    val appModule = module {
        single<OutputPrinter> { DefaultOutputPrinter() }
        single { ProcessExecutor() }
        single { SystemPropertyProvider() }
        single { ByteCountParserProvider() }
        single { TopOutputParserProvider(get()) }
        single { ProcessClassifier() }
        single { ProcessGroupClassifier() }
        single<PsOutputParser> { DefaultPsOutputParser() }
        single { ArgumentParser() }

        // Use-Cases
        single { GetVersionUseCase() }
        single { PrintHelpUseCase(get()) }
        single { GetOsTypeUseCase(get()) }
        single { GetProcessesUseCase(get(), get()) }
        single { GetUsedMemoryUseCase(get(), get()) }
        single { PrintMemoryUsageUseCase() }
        single { AskToKillUseCase() }
        single { KillProcessUseCase(get()) }
        single { GetDataUseCase(get(), get(), get(), get(), get()) }

        // Interactors
        single { MainInteractor(get(), get(), get(), get(), get(), get(), get()) }
    }
}