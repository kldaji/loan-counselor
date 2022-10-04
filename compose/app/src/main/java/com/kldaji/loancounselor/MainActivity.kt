package com.kldaji.loancounselor

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
import com.kldaji.loancounselor.ui.calendar.CalendarScreen
import com.kldaji.loancounselor.ui.client.ReadClientScreen
import com.kldaji.loancounselor.ui.client.WriteClientScreen
import com.kldaji.loancounselor.ui.clients.ClientsScreen
import com.kldaji.loancounselor.ui.scheduledClients.ScheduledClientsScreen
import com.kldaji.loancounselor.ui.search.SearchScreen
import com.kldaji.loancounselor.ui.theme.LoanCounseolorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoanCounseolorTheme {
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
            ClientsScreen(navController = navController)
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
