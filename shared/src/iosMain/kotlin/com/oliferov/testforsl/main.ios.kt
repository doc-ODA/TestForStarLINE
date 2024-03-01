package com.oliferov.testforsl

import androidx.compose.ui.window.ComposeUIViewController
import com.oliferov.testforsl.di.appModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController
import platform.UIKit.UIDevice

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
fun initKoin(){
    startKoin {
        modules(appModule())
    }
}

actual class Platform actual constructor() {
    actual val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}