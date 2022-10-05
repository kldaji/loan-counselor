package com.kldaji.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kldaji.presentation.ui.calendar.CalendarScreen
import com.kldaji.presentation.ui.client.ReadClientScreen
import com.kldaji.presentation.ui.client.WriteClientScreen
import com.kldaji.presentation.ui.clients.ClientsScreen
import com.kldaji.presentation.ui.scheduledClients.ScheduledClientsScreen
import com.kldaji.presentation.ui.search.SearchScreen
import com.kldaji.presentation.ui.theme.LoanCounselorTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoanCounselorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainNavHost()
                }
            }
        }
    }
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "clients",
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("clients") {
            val viewModel = hiltViewModel<ClientsViewModel>()
            ClientsScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = "readClient/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong("id")?.let { id ->
                ReadClientScreen(id = id)
            }
        }
        composable("writeClient") { WriteClientScreen() }
        composable("search") { SearchScreen(/*...*/) }
        composable(
            route = "scheduledClients/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("index")?.let { index ->
                ScheduledClientsScreen(index = index)
            }
        }
        composable("calendar") { CalendarScreen(/*...*/) }
    }
}
