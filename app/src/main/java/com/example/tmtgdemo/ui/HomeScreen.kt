package com.example.tmtgdemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmtgdemo.R
import com.example.tmtgdemo.viewmodel.HomeAction
import com.example.tmtgdemo.viewmodel.HomeActionHandler
import com.example.tmtgdemo.viewmodel.HomeEvent
import com.example.tmtgdemo.viewmodel.HomeViewmodel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewmodel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                is HomeEvent.navigateTo -> {
                    navController.navigate(event.destination)
                }
            }
        }.launchIn(this)
    }

    fun handleAction(action: HomeAction) {
        when (action) {
            is HomeAction.ListOfHomesClicked -> {
                viewModel.actions.trySend(action)
            }
        }
    }

    Home(::handleAction)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(actionHandler: HomeActionHandler) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        bottomBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .testTag("HomeButtonTestTag"),
                    onClick = { actionHandler(HomeAction.ListOfHomesClicked(0)) }) {
                    Text("View Sarasota Homes")
                }
            }
        }
    ) { paddingValue ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.sarasota),
                contentDescription = null,
                modifier = Modifier
                    .padding(paddingValue),
                contentScale = ContentScale.Fit,
            )
        }
    }
}