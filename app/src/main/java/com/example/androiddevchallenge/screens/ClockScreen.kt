/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.theme.pink200
import com.example.androiddevchallenge.utils.makeReadableDuration
import com.example.androiddevchallenge.utils.times
import com.example.androiddevchallenge.viewmodels.ClockViewModel

@Composable
fun ClockScreen(clockViewModel: ClockViewModel = viewModel()) {
    val isPlaying by clockViewModel.isPlaying.observeAsState(false)
    val startTime by clockViewModel.startTime.observeAsState(0L)
    val currentTime by clockViewModel.currentTime.observeAsState(0L)

    var progress by remember { mutableStateOf(1f) }

    progress = if (startTime > 0) {
        currentTime.toFloat() / startTime.toFloat()
    } else {
        1f
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        Clock(progress = progress) {
            TimeText(time = makeReadableDuration(currentTime))
        }
        TimerSelectionList { selectedTime ->
            clockViewModel.setTime(selectedTime)
        }
        PlayPauseButton(
            isPlaying = isPlaying,
            onPlayPauseClick = {
                clockViewModel.setIsPlaying(!isPlaying)
                if (isPlaying) {
                    clockViewModel.startTimer()
                } else {
                    clockViewModel.pauseTimer(currentTime)
                }
            }
        )
    }
}

@Composable
fun TimeText(time: String) {
    Text(
        text = time,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        style = MaterialTheme.typography.h3,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.Monospace
    )
}

@Composable
fun Header() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Clockify",
                maxLines = 1,
                style = MaterialTheme.typography.h3
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Your favorite hardcoded countdown timer ;)",
                maxLines = 1,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Select a time value and press play to begin!",
                maxLines = 2,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Clock(
    progress: Float,
    content: @Composable() () -> Unit
) {
    Box(
        modifier = Modifier
            .size(300.dp)
            .padding(8.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize(),
            progress = progress
        )
        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun TimerSelectionList(onItemClick: (Long) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp)
    ) {
        items(times) { time ->
            TimerItem(millis = time, onItemClick)
        }
    }
}

@Composable
fun TimerItem(millis: Long, onItemClick: (Long) -> Unit) {
    Surface(
        modifier = Modifier
            .height(40.dp)
            .clickable {
                onItemClick(millis)
            },
        color = Color.Gray,
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = makeReadableDuration(millis),
                maxLines = 1,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
fun PlayPauseButton(isPlaying: Boolean, onPlayPauseClick: () -> Unit) {
    Surface(
        color = pink200,
        modifier = Modifier
            .size(70.dp)
            .padding(4.dp)
            .clickable { onPlayPauseClick() },
        elevation = 8.dp,
        shape = CircleShape,
        contentColor = Color.DarkGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Crossfade(targetState = isPlaying) { isPlayingState ->
                    Icon(
                        imageVector = if (isPlayingState) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier = Modifier.fillMaxSize(.8f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ClockPreview() {
    Surface(color = MaterialTheme.colors.background) {
        Clock(1f) {
            TimeText("00:00")
        }
    }
}

@Preview
@Composable
fun TimerItemPreview() {
    Surface(color = MaterialTheme.colors.background) {
        TimerItem(millis = 6000L, { })
    }
}

@Preview
@Composable
fun PlayPauseButtonPreview() {
    Surface(color = MaterialTheme.colors.background) {
        var isPlaying: Boolean by remember { mutableStateOf(false) }

        PlayPauseButton(isPlaying) { isPlaying = !isPlaying }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Surface(color = MaterialTheme.colors.background) {
        Header()
    }
}

@Preview
@Composable
fun TimerSelectionListPreview() {
    Surface(color = MaterialTheme.colors.background) {
        TimerSelectionList {}
    }
}
