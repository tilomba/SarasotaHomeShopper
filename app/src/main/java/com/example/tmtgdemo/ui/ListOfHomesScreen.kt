package com.example.tmtgdemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmtgdemo.R
import com.example.tmtgdemo.data.HomeModel
import com.example.tmtgdemo.viewmodel.ListOfHomesAction
import com.example.tmtgdemo.viewmodel.ListOfHomesActionHandler
import com.example.tmtgdemo.viewmodel.ListOfHomesEvent
import com.example.tmtgdemo.viewmodel.ListOfHomesState
import com.example.tmtgdemo.viewmodel.ListOfHomesViewmodel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ListOfHomesScreen(
    navController: NavController,
    viewModel: ListOfHomesViewmodel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                is ListOfHomesEvent.NavigateTo -> {
                    navController.navigate("${event.destination}/${event.homeId}")
                }
                is ListOfHomesEvent.OnReturn -> {
                    navController.popBackStack()
                }
            }
        }.launchIn(this)
    }

    fun handleAction(action: ListOfHomesAction) {
        when (action) {
            is ListOfHomesAction.OnHomeButtonPressed,
            is ListOfHomesAction.OnHouseSelected -> {
                viewModel.actions.trySend(action)
            }
        }
    }

    val state by viewModel.state.collectAsState(ListOfHomesState.Loading)
    val lazyState = rememberLazyListState()

    when (val readyState = state) {
        is ListOfHomesState.Success -> {
            ListOfHomes(
                actionHandler = ::handleAction,
                readyState.homesDB,
                lazyState
            )
        }

        else -> {}
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfHomes(
    actionHandler: ListOfHomesActionHandler,
    houses: List<HomeModel>,
    lazyState: LazyListState
) {
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
                    .testTag("ReturnHomeTestTag"),
                    onClick = { actionHandler(ListOfHomesAction.OnHomeButtonPressed(0)) }) {
                    Text("Home")
                }
            }
        }
    ) { paddingValue ->
        LazyColumn(
            state = lazyState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = paddingValue
        ) {
            itemsIndexed(houses) { index, house ->
                HomeCard(
                    actionHandler = actionHandler,
                    houseId = house.houseId,
                    address = house.address,
                    mainThumbnail = house.images[0],
                    description = house.description
                )
            }
        }
    }
}

@Composable
fun HomeCard(
    actionHandler: ListOfHomesActionHandler,
    houseId: Int,
    address: String,
    mainThumbnail: Int,
    description: String
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { actionHandler(ListOfHomesAction.OnHouseSelected(houseId)) },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = mainThumbnail),
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}