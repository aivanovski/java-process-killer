package com.github.ai.jpk

import com.github.ai.jpk.di.GlobalInjector.get
import com.github.ai.jpk.di.KoinModule
import com.github.ai.jpk.domain.MainInteractor
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        modules(KoinModule.appModule)
    }

    val interactor: MainInteractor = get()

    interactor.start()
}