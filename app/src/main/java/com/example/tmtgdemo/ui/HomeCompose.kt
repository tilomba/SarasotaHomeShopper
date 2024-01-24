package com.example.tmtgdemo.ui

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmtgdemo.viewmodel.HomeAction
import com.example.tmtgdemo.viewmodel.HomeActionHandler
import com.example.tmtgdemo.viewmodel.HomeEvent
import com.example.tmtgdemo.viewmodel.HomeViewmodel
import com.example.tmtgdemo.viewmodel.ListOfHomesAction
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun HomeScreen(navController: NavController,
               viewModel: HomeViewmodel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when(event) {
                is HomeEvent.navigateTo -> {
                    navController.navigate(event.destination)
                }
            }
        }.launchIn(this)
    }

    fun handleAction(action: HomeAction) {
        when(action) {
            is HomeAction.ListOfHomesClicked -> {
                viewModel.actions.trySend(action)
            }
        }
    }

    Home(::handleAction)
}

@Composable
fun Home(actionHandler: HomeActionHandler) {
    Button(onClick = { actionHandler(HomeAction.ListOfHomesClicked(0)) }) {
        Text("View Sarasota Homes")
    }
}