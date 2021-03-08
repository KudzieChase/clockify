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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.utils.makeReadableDuration
import com.example.androiddevchallenge.utils.times


@Composable
fun ClockScreen() {

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
        Row {
            Text(
                text = "Your favorite hardcoded countdown timer ;)",
                maxLines = 1,
                style = MaterialTheme.typography.h6
            )
        }
        Row {
            Text(
                text = "Select a time value and press play to begin!",
                maxLines = 2,
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Composable
fun Clock(content: @Composable() () -> Unit) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize(),
            progress = 1f
        )
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}


@Composable
fun TimerSelectionList() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp)
    ) {
        items(times) { time ->
            TimerItem(millis = time)
        }
    }
}

@Composable
fun TimerItem(millis: Long) {
    Surface(
        modifier = Modifier
            .height(40.dp),
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
        color = Color.Cyan,
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
                        imageVector = if (isPlayingState) Icons.Filled.PlayArrow else Icons.Filled.Pause,
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
        Clock {
            Text("00:00")
        }
    }
}

@Preview
@Composable
fun TimerItemPreview() {
    Surface(color = MaterialTheme.colors.background) {
        TimerItem(millis = 6000L)
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
        TimerSelectionList()
    }
}
