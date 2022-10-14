package com.kldaji.presentation.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kldaji.presentation.ui.theme.black
import com.kldaji.presentation.ui.theme.blue
import com.kldaji.presentation.ui.theme.green
import com.kldaji.presentation.ui.theme.red
import com.kldaji.presentation.ui.theme.yellow
import com.kldaji.presentation.util.CalendarLogic
import java.util.*

enum class Day(val text: String, val textColor: Color) {
    SUN(text = "일", textColor = red),
    MON(text = "월", textColor = black),
    TUE(text = "화", textColor = black),
    WED(text = "수", textColor = black),
    THR(text = "목", textColor = black),
    FRI(text = "금", textColor = black),
    SAT(text = "토", textColor = blue),
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
) {
    val startPosition = Int.MAX_VALUE / 2
    val state = rememberPagerState(initialPage = startPosition)
    var month by remember { mutableStateOf("${CalendarLogic.getMonth(0)}월") }

    Scaffold(
        topBar = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                }
                Text(
                    text = month,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Column(
                    modifier = modifier.padding(end = 16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(yellow)
                        )
                        Text(
                            modifier = modifier.padding(start = 8.dp),
                            text = "미팅",
                            fontSize = 12.sp
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(green)
                        )
                        Text(
                            modifier = modifier.padding(start = 8.dp),
                            text = "대출실행",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            count = Int.MAX_VALUE,
            state = state
        ) { page: Int ->

            month = "${CalendarLogic.getMonth(currentPage - startPosition)}월"

            Column(modifier = modifier.fillMaxSize()) {
                Days(modifier = modifier)

                val timestamp = CalendarLogic.getFirstDateOfMonthTimestamp(page - startPosition)
                Month(
                    modifier = modifier,
                    firstDate = Date(timestamp)
                )
            }
        }
    }
}

@Composable
fun Days(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Day.values().forEach { day ->
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = day.text,
                    color = day.textColor,
                )
            }
        }
    }
}

@Composable
fun Month(
    modifier: Modifier,
    firstDate: Date,
) {
    val dates = CalendarLogic.getDateList(firstDate)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        dates.chunked(7).forEach { week ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                week.forEach { date ->
                    this@Column.Date(
                        modifier = modifier,
                        firstDate = firstDate,
                        date = date,
                    )
                }
            }
        }
    }
}

@Composable
fun ColumnScope.Date(
    modifier: Modifier,
    firstDate: Date,
    date: Date,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = CalendarLogic.getDateNumber(date),
            color = if (CalendarLogic.isSameMonth(firstDate,
                    date)
            ) black else black.copy(alpha = 0.5f)
        )
    }
}
