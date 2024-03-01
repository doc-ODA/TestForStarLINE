package com.oliferov.testforsl

import androidx.compose.runtime.Composable

@Composable
expect fun MapLibreView(
    marker: Pair<Double, Double>,
    centre: Pair<Double, Double>?,
    title: String,
    zoom: Double,
    zoomCallback: (Double) -> Unit,
    centreCallback: (Pair<Double, Double>) -> Unit
)