package com.oliferov.testforsl

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect class Platform(){
    val name: String
}