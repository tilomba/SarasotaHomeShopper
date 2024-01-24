package com.example.tmtgdemo.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.tmtgdemo.data.HomeModel
import com.example.tmtgdemo.repository.HouseDetailRepository
import com.example.tmtgdemo.util.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfHomesViewmodel @Inject constructor(
    private val houseDetailRepository: HouseDetailRepository
) : BaseViewModel<ListOfHomesAction, ListOfHomesState, ListOfHomesEvent>(ListOfHomesState.Loading) {

    init {
        getListOfHouses()
    }

    override fun handleAction(action: ListOfHomesAction) {
        when (action) {
            is ListOfHomesAction.OnHomeButtonPressed -> {
                _eventChannel.trySend(ListOfHomesEvent.OnReturn(Screens.HOME))
            }

            is ListOfHomesAction.OnHouseSelected -> {
                _eventChannel.trySend(
                    ListOfHomesEvent.NavigateTo(
                        Screens.HOUSEDETAIL,
                        action.houseId
                    )
                )
            }
        }
    }

    private fun getListOfHouses() {
        viewModelScope.launch {
            when (val response = houseDetailRepository.getListOfHouses()) {
                is ListOfHomesState.Success -> {
                    if (state.value !is ListOfHomesState.Error) {
                        _state.value = ListOfHomesState.Success(
                            homesDB = response.homesDB
                        )
                    }
                }

                else -> {
                    _state.value = ListOfHomesState.Error(1, "Error loading housing database")
                }
            }
        }
    }

}

typealias ListOfHomesActionHandler = (ListOfHomesAction) -> Unit

sealed class ListOfHomesAction : ViewAction {
    data class OnHomeButtonPressed(val homeId: Int) : ListOfHomesAction()
    data class OnHouseSelected(val houseId: Int) : ListOfHomesAction()
}

sealed class ListOfHomesState : ViewState {
    data class Success(val homesDB: List<HomeModel>) : ListOfHomesState()
    data class Error(val errorCode: Int, val errorMessage: String) : ListOfHomesState()
    object Loading : ListOfHomesState()
}

sealed class ListOfHomesEvent : ViewEvent {
    data class NavigateTo(val destination: String, val homeId: Int) : ListOfHomesEvent()
    data class OnReturn(val destination: String) : ListOfHomesEvent()
}