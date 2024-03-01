package com.oliferov.testforsl.devicesFeature.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.oliferov.testforsl.devicesFeature.domain.model.CarDeviceDomain
import com.oliferov.testforsl.devicesFeature.domain.model.GroupDeviceDomain
import com.oliferov.testforsl.devicesFeature.presentation.store.DevicesActions
import com.oliferov.testforsl.devicesFeature.presentation.store.DevicesEvents
import com.oliferov.testforsl.devicesFeature.presentation.store.DevicesStore
import com.oliferov.testforsl.mapFeature.view.MapScreen
import com.oliferov.testforsl.theme.primary_default
import com.oliferov.testforsl.theme.primary_disabled
import com.oliferov.testforsl.theme.primary_pressed
import com.oliferov.testforsl.theme.title_devices_screen
import com.oliferov.testforsl.theme.update_list
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class DevicesScreen : Screen {
    @Composable
    override fun Content() {

        val store: DevicesStore = koinInject()
        val uiState by store.observeState().collectAsState()
        val coroutineScope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            coroutineScope.launch {
                store.observeEvent().collect {
                    when (it) {
                        is DevicesEvents.SelectedDevice -> {
                            coroutineScope.launch {
                                with(it.device) {
                                    navigator.push(MapScreen(title = title, lat = lat, lon = lon))
                                }
                            }

                        }
                    }
                }
            }
        }

        DevicesView(
            isLoading = uiState.isLoading,
            devices = uiState.devaces,
            onClickCallback = {
                store.handle(DevicesActions.LoadDevices)
            },
            selectedDeviceCallback = {
                store.handle(DevicesActions.SelectedDevice(it))
            }
        )
    }

    @Composable
    private fun DevicesView(
        isLoading: Boolean,
        devices: List<GroupDeviceDomain> = emptyList(),
        onClickCallback: () -> Unit = {},
        selectedDeviceCallback: (CarDeviceDomain) -> Unit = {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.Red)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = title_devices_screen,
                    style = typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .background(color = Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(devices) { item ->
                        bindGroupCard(group = item, selectedDeviceCallback = selectedDeviceCallback)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                UpdateButton(isLoading = isLoading, onClickCallback = onClickCallback)
            }
        }
    }

    @Composable
    fun bindGroupCard(group: GroupDeviceDomain, selectedDeviceCallback: (CarDeviceDomain) -> Unit) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                group.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            group.cars.forEach {
                CarCard(car = it, selectedDeviceCallback = selectedDeviceCallback)
            }
        }

    }

    @Composable
    fun CarCard(car: CarDeviceDomain, selectedDeviceCallback: (CarDeviceDomain) -> Unit) {
        Card(
            shape = RoundedCornerShape(4.dp),
            elevation = CardDefaults.elevatedCardElevation(2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable {
                    selectedDeviceCallback(car)
                }
        ) {
            Text(
                text = car.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }

    @Composable
    private fun UpdateButton(
        isLoading: Boolean,
        onClickCallback: () -> Unit = {}
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val color = if (isPressed)
            primary_pressed
        else
            primary_default
        Button(
            onClick = {
                onClickCallback()
            },
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp
            ),
            shape = shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
                disabledContainerColor = primary_disabled,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            interactionSource = interactionSource,
            enabled = !isLoading
        ) {
            Text(
                text = update_list,
                color = Color.White,
                style = typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

