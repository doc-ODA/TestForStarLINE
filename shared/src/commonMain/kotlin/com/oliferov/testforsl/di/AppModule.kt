package com.oliferov.testforsl.di

import org.koin.core.module.Module

fun appModule(): List<Module> = listOf<Module>(platformModule).plus(commonModule())