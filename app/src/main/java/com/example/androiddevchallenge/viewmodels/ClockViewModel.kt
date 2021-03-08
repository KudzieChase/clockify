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
package com.example.androiddevchallenge.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClockViewModel : ViewModel() {

    private val _startTime = MutableLiveData<Long>(0)
    val startTime: LiveData<Long>
        get() = _startTime

    private val _currentTime = MutableLiveData<Long>(0)
    val currentTime: LiveData<Long>
        get() = _currentTime

    private val _isPlaying = MutableLiveData<Boolean>(false)
    val isPlaying: LiveData<Boolean>
        get() = _isPlaying

    private var timer: CountDownTimer? = null

    init {
        _currentTime.value = 0L
    }

    fun setIsPlaying(isPlayingState: Boolean) {
        _isPlaying.value = isPlayingState
    }

    fun setTime(timeValue: Long) {
        _startTime.value = timeValue
        _currentTime.value = timeValue
    }

    fun startTimer() {
        _currentTime.value?.let {
            timer = object : CountDownTimer(it, 1000) {
                override fun onTick(millis: Long) {
                    _currentTime.value = millis
                }

                override fun onFinish() {
                    _currentTime.value = 0L
                }
            }

            timer?.start()
        }
    }

    fun pauseTimer(currentTimeState: Long) {
        timer?.cancel()
        _currentTime.value = currentTimeState
    }

    fun clear() {
        timer?.cancel()
        _currentTime.value = 0L
        _startTime.value = 0L
    }
}
