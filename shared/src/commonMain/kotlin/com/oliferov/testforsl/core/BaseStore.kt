package com.oliferov.testforsl.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseStore<S : State, A : Actions, Ef : Effects, Ev : Events>(
    initialState: S,
    private val updater: StateUpdater<S, A, Ef, Ev>,
    private val initialEffects: Set<Ef> = emptySet(),
    private val processor: Processor<A, Ef>
) : Store<S, A, Ef, Ev>, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    init {
        processEffects(initialEffects)
    }

    private val events = MutableSharedFlow<Ev>()
    private val state = MutableStateFlow(initialState)
    override fun observeState(): StateFlow<S> = state

    override fun observeEvent(): Flow<Ev> = events

    override fun handle(action: A) {
        val newState = updater.update(action, state.value)

        if (newState.effects.isNotEmpty()) {
            processEffects(newState.effects)
        }

        if (newState.events.isNotEmpty()) {
            launch {
                newState.events.forEach {
                    events.emit(it)
                }
            }
        }

        state.value = newState.state
    }

    private fun processEffects(effects: Set<Ef>) {
        effects.forEach {

            launch(Dispatchers.IO) {
                val newAction = processor.process(it)

                withContext(Dispatchers.Main) {
                    handle(newAction)
                }
            }
        }
    }
}