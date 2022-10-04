package com.kldaji.loancounselor.ui.clients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ClientsScreen(
    modifier: Modifier = Modifier,
    onNavigateToReadClient: (id: Int) -> Unit,
    onNavigateToWriteClient: () -> Unit,
    onNavigateToSearchClient: () -> Unit,
    onNavigateToScheduledClients: (index: Int) -> Unit,
    onNavigateToCalendar: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("clients screen")
    }
}
