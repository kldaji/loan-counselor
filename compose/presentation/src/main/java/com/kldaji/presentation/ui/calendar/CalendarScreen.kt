package com.kldaji.presentation.ui.calendar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kldaji.presentation.ui.theme.black
import com.kldaji.presentation.ui.theme.blue
import com.kldaji.presentation.ui.theme.red
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

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            modifier = modifier.fillMaxSize(),
            count = Int.MAX_VALUE,
            state = state
        ) { page: Int ->
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
                    .weight(1f)
                    .border(width = 1.dp, color = red),
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
            .weight(1f)
            .border(width = 1.dp, color = black),
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
