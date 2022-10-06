package com.kldaji.presentation.ui.client

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.kldaji.domain.Client
import com.kldaji.presentation.ClientsViewModel

@Composable
fun WriteClientScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ClientsViewModel,
) {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    val photos by viewModel.pictures.observeAsState(listOf())
    val client = Client()

    Scaffold(
        topBar = {
            WriteClientTopBar(
                modifier = modifier,
                onPopBackStack = navController::popBackStack,
                client = client,
                onAddClient = viewModel::insertClient
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier.padding(paddingValues)
        ) {
            item {
                WriteClientPhotos(
                    modifier = modifier,
                    isExpanded = isDropDownMenuExpanded,
                    onSetIsExpanded = { isDropDownMenuExpanded = it },
                    photos = photos,
                    onAddPhoto = viewModel::addPicture
                )
            }
        }
    }
}

@Composable
fun WriteClientTopBar(
    modifier: Modifier,
    onPopBackStack: () -> Unit,
    client: Client,
    onAddClient: (Client) -> Unit
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
                onAddClient(client)
                onPopBackStack()
            }
        )
    }
}

@Composable
fun WriteClientPhotos(
    modifier: Modifier,
    isExpanded: Boolean,
    onSetIsExpanded: (Boolean) -> Unit,
    photos: List<String>,
    onAddPhoto: (String) -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            Box(
                modifier
                    .size(width = 60.dp, height = 60.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { onSetIsExpanded(true) }) {
                    Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = "사진 추가")
                }
            }
            
            DropdownMenu(expanded = isExpanded, onDismissRequest = { onSetIsExpanded(false) }) {
                DropdownMenuItem(onClick = {
                    onSetIsExpanded(false)
                }) {
                    Text(text = "사진 촬영")
                }
                DropdownMenuItem(onClick = {
                    onSetIsExpanded(false)
                }) {
                    Text(text = "갤러리")
                }
            }
        }

        items(
            count = photos.size
        ) {
            WriteClientPhotoItem(modifier = modifier, photo = photos[it])
        }
    }
}

@Composable
fun WriteClientPhotoItem(modifier: Modifier, photo: String) {
    val uri = Uri.parse(photo)

    Image(
        painter = rememberImagePainter(data = uri),
        contentDescription = "이미지",
    )

    Box(
        modifier = modifier.size(width = 65.dp, height = 65.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.HighlightOff, contentDescription = "삭제")
        }
    }
}
