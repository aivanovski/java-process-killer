package com.github.ai.jpkiller

import com.github.ai.jpkiller.di.GlobalInjector.get
import com.github.ai.jpkiller.di.KoinModule
import com.github.ai.jpkiller.domain.MainInteractor
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        modules(KoinModule.appModule)
    }

    val interactor: MainInteractor = get()

    interactor.start()
}