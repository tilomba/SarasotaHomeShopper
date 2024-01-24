package com.example.tmtgdemo.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.tmtgdemo.data.HomeModel
import com.example.tmtgdemo.repository.HouseDetailRepository
import com.example.tmtgdemo.repository.HouseDetailRepositoryImpl
import com.example.tmtgdemo.util.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseDetailViewmodel @Inject constructor(
    private val houseDetailRepository: HouseDetailRepository,
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<HouseDetailAction, HouseDetailState, HouseDetailEvent>(HouseDetailState.Loading) {
    private val houseId: Int? = savedStateHandle["houseId"]

    init {
        Log.d("TMTGLog", "houseId = ${houseId}")
        houseId?.let{
            getHouseImages(it)
        }
    }

    override fun handleAction(action: HouseDetailAction) {
        when (action) {
            is HouseDetailAction.OnBackButtonPressed -> {
                _eventChannel.trySend(HouseDetailEvent.NavigateTo(Screens.LISTOFHOMES))
            }
        }
    }

    private fun getHouseImages(houseId: Int) {
        viewModelScope.launch {
            when(val response = houseDetailRepository.getHouse(houseId)) {
                is HouseDetailState.Success -> {
                    if(state.value !is HouseDetailState.Error) {
                        _state.value = HouseDetailState.Success(
                            house = response.house
                        )
                    }
                }
                else -> {
                    _state.value = HouseDetailState.Error(1, "Error loading housing database")
                }
            }
        }
    }

}

typealias HouseDetailActionHandler = (HouseDetailAction) -> Unit

sealed class HouseDetailAction : ViewAction {
    data class OnBackButtonPressed(val homeId: Int) : HouseDetailAction()
}

sealed class HouseDetailState : ViewState {
    data class Success(
        val house: HomeModel
    ) : HouseDetailState()

    data class Error(val errorCode: Int, val errorMessage: String) : HouseDetailState()
    object Loading : HouseDetailState()
}

sealed class HouseDetailEvent : ViewEvent {
    data class NavigateTo(val destination: String) : HouseDetailEvent()
}