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
package com.example.androiddevchallenge.utils

import java.util.concurrent.TimeUnit

// Hardcoded Millis
val times: List<Long> = listOf(
    convertToMillis(min = 3, second = 20),
    convertToMillis(min = 1, second = 20),
    convertToMillis(min = 4, second = 20),
    convertToMillis(min = 1),
    convertToMillis(second = 45),
    convertToMillis(min = 4, second = 35),
    convertToMillis(hour = 1, min = 30, second = 20),
)

fun makeReadableDuration(millis: Long): String {
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    val hours = TimeUnit.MILLISECONDS.toHours(millis)

    return if (hours > 0L)
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    else
        String.format("%02d:%02d", minutes, seconds)
}

// This function is definitely a code smell...
// I never validate the range of the input coz its for hardcoding purposes.
fun convertToMillis(hour: Long = 0L, min: Long = 0L, second: Long = 0L): Long {
    var finalMillis = 0L
    if (hour > 0) {
        finalMillis += hour * 60 * 60 * 1000
    }

    if (min > 0) {
        finalMillis += min * 60 * 1000
    }

    if (second > 0) {
        finalMillis += second * 1000
    }

    return finalMillis
}
