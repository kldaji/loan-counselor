package com.kldaji.presentation.ui.client

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
    val contentResolver = LocalContext.current.contentResolver
    var uri: Uri? by remember {
        mutableStateOf(null)
    }
    val getContentLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
            uri = it
        }
    uri?.let {
        contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        viewModel.addPicture(it.toString())
    }
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    val photos by viewModel.pictures.observeAsState(listOf())

    Scaffold(
        topBar = {
            WriteClientTopBar(
                modifier = modifier,
                onPopBackStack = navController::popBackStack,
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
                    onAddPhoto = {
                        getContentLauncher.launch(arrayOf("image/*"))
                    },
                )
            }

            item {
                ClientInfo(modifier = modifier)
            }
        }
    }
}

@Composable
fun WriteClientTopBar(
    modifier: Modifier,
    onPopBackStack: () -> Unit,
    onAddClient: (Client) -> Unit,
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
                onAddClient(
                    Client(
                        name = "Kim",
                        birth = "970703"
                    )
                )
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
    onAddPhoto: () -> Unit,
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
                    onAddPhoto()
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
        modifier = modifier
            .padding(start = 16.dp)
            .size(width = 60.dp, height = 60.dp),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun ClientInfo(
    modifier: Modifier
) {
    Divider(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        color = Color.DarkGray
    )

    val radioOptions = listOf("담보대출", "전세대출")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        radioOptions.forEach { text ->
            Row(
                modifier = modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
                Text(
                    text = text,
                    fontSize = 14.sp
                )
            }
        }
    }
}
