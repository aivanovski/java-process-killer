package com.github.ai.fmstudio

import com.github.ai.fmstudio.di.GlobalInjector.get
import com.github.ai.fmstudio.di.KoinModule
import com.github.ai.fmstudio.domain.MainInteractor
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        modules(KoinModule.appModule)
    }

    val interactor: MainInteractor = get()

    interactor.start()
}