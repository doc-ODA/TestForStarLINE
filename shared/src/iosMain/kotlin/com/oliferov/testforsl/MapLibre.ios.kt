package com.oliferov.testforsl

import androidx.compose.runtime.Composable
import platform.MapKit.MKMapView
import platform.MapKit.*
import platform.MapKit.MKPointAnnotation
import androidx.compose.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import com.oliferov.testforsl.devicesFeature.data.DevicesRepositoryImpl
import com.oliferov.testforsl.devicesFeature.domain.DevicesRepository
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.pin
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.UIKit.UIViewController
import platform.darwin.NSObject

private class MKDelegate(
    private val onAnnotationClicked: (MKPointAnnotation?) -> Unit
) : NSObject(), MKMapViewDelegateProtocol {
    override fun mapView(mapView: MKMapView, didSelectAnnotationView: MKAnnotationView) {
        val annotation = didSelectAnnotationView.annotation as MKPointAnnotation
        onAnnotationClicked(annotation)
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapLibreView(
    marker: Pair<Double, Double>,
    centre: Pair<Double, Double>?,
    title: String,
    zoom: Double,
    zoomCallback: (Double) -> Unit,
    centreCallback: (Pair<Double, Double>) -> Unit
) {
    val delegate = remember {
        MKDelegate { annotation ->
            val clickedTitle = annotation?.title

            return@MKDelegate
        }
    }
    val overlay = MKTileOverlay(STYLE)
    val coordinateCenter = CLLocationCoordinate2DMake(latitude = marker.first, longitude = marker.second)
    val mapCamera = MKMapCamera.cameraLookingAtCenterCoordinate(coordinateCenter, coordinateCenter, 800.0)

    UIKitView(
        factory = {
            MKMapView().apply {
                setDelegate(delegate)
                overlay.canReplaceMapContent()
                addOverlay(overlay)
                rendererForOverlay(overlay)
                setCamera(mapCamera, true)
                overlay.setCanReplaceMapContent(true)
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = {

            val pin = MKPointAnnotation()
            pin.setCoordinate(coordinateCenter)
            pin.setTitle(title)

            it.addAnnotation(pin)
        }
    )
}

private const val STYLE = "https://maps.starline.ru/mapstyles/default/style.json"