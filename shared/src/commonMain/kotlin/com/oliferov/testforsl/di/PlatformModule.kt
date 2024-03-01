package com.oliferov.testforsl.di

import com.oliferov.testforsl.Platform
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val platformModule = module {
    singleOf(::Platform)
}