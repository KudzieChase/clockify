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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.screens.components.Clock
import com.example.androiddevchallenge.screens.components.Header
import com.example.androiddevchallenge.screens.components.PlayPauseButton
import com.example.androiddevchallenge.screens.components.TimeText
import com.example.androiddevchallenge.screens.components.TimerSelectionList
import com.example.androiddevchallenge.utils.makeReadableDuration
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
        Spacer(Modifier.height(12.dp))
    }
}
