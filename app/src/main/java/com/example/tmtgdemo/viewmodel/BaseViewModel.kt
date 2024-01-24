package com.example.tmtgdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<Action: ViewAction, State: ViewState, Event: ViewEvent> (
    private val defaultState: State
) : ViewModel() {
     // events
    protected val _eventChannel = Channel<Event>(capacity = UNLIMITED)
    protected val _events : SendChannel<Event> = _eventChannel
    val events : Flow<Event> = _eventChannel.receiveAsFlow().onEach {
        publishEvent(it)
    }

    protected fun sendEvent(event: Event) {
        _events.trySend(event)
    }

    private fun publishEvent(event: Event) {
        // Log
    }

    // actions
    private val _actions = Channel<Action>(capacity = UNLIMITED)
    val actions : SendChannel<Action> = _actions

    protected abstract fun handleAction(action: Action)
    private fun publishAction(action: Action) {
        // Log
    }
    fun sendAction(action: Action) {
        _actions.trySend(action)
    }

    // state
    protected val _state = MutableStateFlow(defaultState)
    val state : StateFlow<State> = _state

    protected fun updateState(state: (State) -> State) {
        _state.value = state(_state.value)
    }

    init {
        _actions.consumeAsFlow().onEach {
            handleAction(it)
            publishAction(it)
        }.launchIn(viewModelScope)
    }
}



interface ViewAction
interface ViewEvent
interface ViewState