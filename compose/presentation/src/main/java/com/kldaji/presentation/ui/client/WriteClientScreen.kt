package com.kldaji.presentation.ui.client

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kldaji.presentation.ClientsViewModel

@Composable
fun WriteClientScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ClientsViewModel,
) {
    Scaffold(
        topBar = {
            WriteClientTopBar(
                modifier = modifier,
                onPopBackStack = navController::popBackStack
            )
        }
    ) { paddingValues ->
        LazyRow(
            modifier = modifier.padding(paddingValues)
        ) {

        }
    }
}

@Composable
fun WriteClientTopBar(
    modifier: Modifier,
    onPopBackStack: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onPopBackStack() }) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "뒤로가기"
            )
        }

        Text(
            text = "고객 정보 추가",
            color = Color.Black,
            fontSize = 22.sp
        )

        Text(
            text = "완료",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = modifier.clickable {

                onPopBackStack()
            }
        )
    }
}
