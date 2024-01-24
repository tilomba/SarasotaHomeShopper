package com.example.tmtgdemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmtgdemo.R
import com.example.tmtgdemo.viewmodel.HouseDetailAction
import com.example.tmtgdemo.viewmodel.HouseDetailActionHandler
import com.example.tmtgdemo.viewmodel.HouseDetailEvent
import com.example.tmtgdemo.viewmodel.HouseDetailState
import com.example.tmtgdemo.viewmodel.HouseDetailViewmodel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun HouseDetailScreen(
    navController: NavController,
    viewModel: HouseDetailViewmodel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                is HouseDetailEvent.NavigateTo -> {
                    navController.popBackStack()
                }
            }
        }.launchIn(this)
    }

    fun handleAction(action: HouseDetailAction) {
        when (action) {
            is HouseDetailAction.OnBackButtonPressed -> {
                viewModel.actions.trySend(action)
            }
        }
    }

    val state by viewModel.state.collectAsState(HouseDetailState.Loading)

    when (val readyState = state) {
        is HouseDetailState.Success -> {
            HouseDetail(
                actionHandler = ::handleAction,
                readyState.house.images
            )
        }

        else -> {

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseDetail(
    actionHandler: HouseDetailActionHandler,
    images: List<Int>
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
                Button(modifier = Modifier.fillMaxWidth(),
                    onClick = { actionHandler(HouseDetailAction.OnBackButtonPressed(0)) }) {
                    Text("Return")
                }
            }
        }
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = paddingValue
        ) {
            itemsIndexed(images) { index, imageId ->
                HouseDetailCard(
                    actionHandler,
                    imageId
                )
            }
        }

    }
}

@Composable
fun HouseDetailCard(actionHandler: HouseDetailActionHandler, imageId: Int) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
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
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier
//                    .size(130.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
            )
        }
    }
}