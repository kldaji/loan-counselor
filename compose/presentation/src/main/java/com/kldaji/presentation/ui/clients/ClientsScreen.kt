package com.kldaji.presentation.ui.clients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kldaji.domain.Client
import com.kldaji.presentation.ClientsViewModel

@Composable
fun ClientsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ClientsViewModel,
) {
    viewModel.fetchClients()
    val clients by viewModel.clients.observeAsState(listOf())

    Scaffold(
        topBar = {
            ClientsTopBar(
                modifier = modifier,
                onNavigateToSearch = {
                    navController.navigate("search")
                },
                onNavigateToWriteClient = {
                    navController.navigate("writeClient")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("calendar") }) {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "캘런더 화면으로 이동")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = modifier.padding(paddingValues)) {
            item {
                ScheduledClientsTab(
                    modifier = modifier,
                    index = 0,
                    navController = navController
                )
            }
            item {
                ScheduledClientsTab(
                    modifier = modifier,
                    index = 1,
                    navController = navController
                )
            }
            item {
                ClientsHeader(
                    modifier = modifier,
                    count = clients.size
                )
            }
            items(
                count = clients.size
            ) { index ->
                ClientItem(
                    modifier = modifier,
                    client = clients[index]
                )
            }
        }
    }
}

@Composable
fun ClientsTopBar(
    modifier: Modifier,
    onNavigateToSearch: () -> Unit,
    onNavigateToWriteClient: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = modifier.padding(16.dp),
            text = "고객",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateToSearch,
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "고객 검색",
                )
            }
            IconButton(
                onClick = onNavigateToWriteClient,
                modifier = modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAddAlt1,
                    contentDescription = "고객 추가",
                )
            }
        }
    }
}

@Composable
fun ScheduledClientsTab(
    modifier: Modifier,
    index: Int,
    navController: NavController,
) {
    Divider(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        color = Color.DarkGray
    )

    Column(
        modifier = modifier.padding(start = 16.dp, top = 8.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        if (index == 0) {
            ScheduledClientsTabContent(
                modifier = modifier,
                subTitle = "미팅 예정 고객",
                imageVector = Icons.Default.PeopleAlt,
                title = "오늘의 미팅 예정 고객 명단 입니다!",
                onNavigateToScheduledClients = {
                    navController.navigate("scheduledClients/$index")
                }
            )
        } else {
            ScheduledClientsTabContent(
                modifier = modifier,
                subTitle = "대출실행 예정 고객",
                imageVector = Icons.Default.Send,
                title = "30일내 대출실행 예정 고객 명단 입니다!",
                onNavigateToScheduledClients = {
                    navController.navigate("scheduledClients/$index")
                }
            )
        }
    }
}

@Composable
fun ScheduledClientsTabContent(
    modifier: Modifier,
    subTitle: String,
    imageVector: ImageVector,
    title: String,
    onNavigateToScheduledClients: () -> Unit,
) {
    Text(
        text = subTitle,
        color = Color.DarkGray,
        fontSize = 12.sp
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = imageVector,
                contentDescription = "스케줄 예정 고객 아이콘",
                modifier = modifier.padding(end = 8.dp)
            )
            Text(
                text = title,
                color = Color.Black,
                fontSize = 16.sp
            )
        }
        IconButton(onClick = { onNavigateToScheduledClients() }) {
            Icon(
                imageVector = Icons.Default.NavigateNext,
                contentDescription = "스케줄 예정 고객 화면 이동"
            )
        }
    }
}

@Composable
fun ClientsHeader(modifier: Modifier, count: Int) {
    Divider(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        color = Color.DarkGray
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier.padding(end = 2.dp),
            text = "고객",
            color = Color.DarkGray,
            fontSize = 12.sp
        )
        Text(
            text = count.toString(),
            color = Color.DarkGray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ClientItem(
    modifier: Modifier,
    client: Client,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "프로필",
                modifier = modifier
                    .size(50.dp)
                    .padding(end = 16.dp)
            )
            Text(
                text = client.name,
                color = Color.Black,
                fontSize = 20.sp
            )
        }
        Text(
            text = client.birth,
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}
