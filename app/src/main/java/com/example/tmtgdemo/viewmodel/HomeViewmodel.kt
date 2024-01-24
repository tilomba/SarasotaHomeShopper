package com.example.tmtgdemo.viewmodel

import com.example.tmtgdemo.util.Screens
import javax.inject.Inject

class HomeViewmodel @Inject constructor() : BaseViewModel<HomeAction, HomeState, HomeEvent>(HomeState.Loading) {
    override fun handleAction(action: HomeAction) {
        when(action) {
            is HomeAction.ListOfHomesClicked ->
            {
                _eventChannel.trySend(HomeEvent.navigateTo(Screens.LISTOFHOMES))
            }
        }
    }
}

typealias HomeActionHandler  = (HomeAction) -> Unit

sealed class HomeAction : ViewAction {
    data class ListOfHomesClicked (val homeId: Int) : HomeAction()
}

sealed class HomeState : ViewState {
    data class Success (val homeId: Int) : HomeState()
    data class Error (val errorCode: Int, val errorMessage : String) : HomeState()
    object Loading : HomeState()
}

sealed class HomeEvent : ViewEvent {
    data class navigateTo(val destination: String) : HomeEvent()
}