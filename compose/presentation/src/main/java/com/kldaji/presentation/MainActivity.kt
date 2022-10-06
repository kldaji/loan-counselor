package com.kldaji.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
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

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isCameraPermissionGranted = false
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLauncher()
        requestPermission()

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

    private fun setLauncher() {
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                isCameraPermissionGranted =
                    permissions[Manifest.permission.CAMERA] ?: isCameraPermissionGranted
                isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE]
                    ?: isReadPermissionGranted
                isWritePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]
                    ?: isWritePermissionGranted

                if (!isCameraPermissionGranted || !isReadPermissionGranted || !isWritePermissionGranted) {
                    // TODO: 권한 설정 화면으로 이동
                    finish()
                }
            }
    }

    private fun requestPermission() {
        isCameraPermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        isReadPermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        isWritePermissionGranted = (ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)

        val permissions = mutableListOf<String>()

        if (!isCameraPermissionGranted) permissions.add(Manifest.permission.CAMERA)
        if (!isReadPermissionGranted) permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!isWritePermissionGranted) permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permissions.isNotEmpty()) permissionLauncher.launch(permissions.toTypedArray())
    }
}


@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "clients",
) {
    val viewModel = hiltViewModel<ClientsViewModel>()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("clients") {
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
        composable("writeClient") {
            WriteClientScreen(navController = navController, viewModel = viewModel)
        }
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
