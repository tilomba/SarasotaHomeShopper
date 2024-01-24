package com.example.tmtgdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tmtgdemo.ui.HomeScreen
import com.example.tmtgdemo.ui.HouseDetailScreen
import com.example.tmtgdemo.ui.ListOfHomesScreen
import com.example.tmtgdemo.ui.theme.TMTGDemoTheme
import com.example.tmtgdemo.util.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            TMTGDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SarasotaHomesFinderCompose()
                }
            }
        }
    }
}


// Define the MyApp composable, including the `NavController` and `NavHost`.
@Composable
fun SarasotaHomesFinderCompose() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.HOME) {
        composable(Screens.HOME) { HomeScreen(navController = navController) }
        composable(Screens.LISTOFHOMES) { ListOfHomesScreen(navController = navController) }
        composable(
            route = "${Screens.HOUSEDETAIL}/{houseId}",
            arguments = listOf(navArgument("houseId") { type = NavType.IntType })
        ) {
            HouseDetailScreen(navController = navController)
        }
    }
}
