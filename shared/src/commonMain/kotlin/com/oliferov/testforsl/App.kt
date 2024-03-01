package com.oliferov.testforsl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliferov.testforsl.theme.AppTheme
import org.koin.compose.KoinContext
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.oliferov.testforsl.devicesFeature.presentation.view.DevicesScreen
import org.koin.compose.koinInject

@Composable
fun App() = AppTheme {
    KoinContext {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Navigator(DevicesScreen()) { navigator ->
                SlideTransition(navigator)
            }
        }
    }
}
