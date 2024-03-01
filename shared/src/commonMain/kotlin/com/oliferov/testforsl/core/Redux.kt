package com.oliferov.testforsl.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface State
interface Actions {
    object Nothing : Actions
}

interface Effects
interface Events

interface Processor< A : Actions, Ef : Effects> {
    suspend fun process(effect: Ef): A
}

data class StateHolder<S: State, Ef : Effects, Ev : Events>(
    val state: S,
    val effects: Set<Ef> = emptySet(),
    val events: Set<Ev> = emptySet()
)

interface StateUpdater<S : State, A : Actions, Ef : Effects, Ev : Events> {
    fun update(action: A, currentState: S): StateHolder<S, Ef, Ev>

    abstract class Abstract<S : State, A : Actions, Ef : Effects, Ev : Events> :
        StateUpdater<S, A, Ef, Ev> {

        override fun update(action: A, currentState: S): StateHolder<S, Ef, Ev> {
            if (action is Actions.Nothing) {
                return StateHolder(currentState)
            } else {
                throw IllegalStateException("Unsupported action")
            }
        }
    }
}

interface Store<S : State, A : Actions, Ef : Effects, Ev : Events> {
    fun observeState(): StateFlow<S>
    fun observeEvent(): Flow<Ev>
    fun handle(action: A)
}