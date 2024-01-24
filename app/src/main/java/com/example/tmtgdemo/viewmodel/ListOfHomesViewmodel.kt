package com.example.tmtgdemo.viewmodel

import com.example.tmtgdemo.util.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListOfHomesViewmodel @Inject constructor() : BaseViewModel<ListOfHomesAction, ListOfHomesState, ListOfHomesEvent>(ListOfHomesState.Loading) {
    override fun handleAction(action: ListOfHomesAction) {
        when(action) {
            is ListOfHomesAction.OnHomeButtonPressed ->
            {
                _eventChannel.trySend(ListOfHomesEvent.OnReturn(Screens.HOME))
            }
            is ListOfHomesAction.OnHouseSelected ->
            {
                _eventChannel.trySend(ListOfHomesEvent.NavigateTo(Screens.HOUSEDETAIL, action.houseId))
            }
        }
    }
}

typealias ListOfHomesActionHandler  = (ListOfHomesAction) -> Unit

sealed class ListOfHomesAction : ViewAction {
    data class OnHomeButtonPressed (val homeId: Int) : ListOfHomesAction()
    data class OnHouseSelected (val houseId: Int) : ListOfHomesAction()
}

sealed class ListOfHomesState : ViewState {
    data class Success (val homeId: Int) : ListOfHomesState()
    data class Error (val errorCode: Int, val errorMessage : String) : ListOfHomesState()
    object Loading : ListOfHomesState()
}

sealed class ListOfHomesEvent : ViewEvent {
    data class NavigateTo(val destination: String, val homeId: Int) : ListOfHomesEvent()
    data class OnReturn(val destination: String) : ListOfHomesEvent()
}