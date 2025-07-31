package com.merveylcu.core.common.detector

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserInteractionDetector(private val onUserInactive: () -> Unit) {
    private var lastInteractionTime = System.currentTimeMillis()
    private val idleThreshold = 5 * 60 * 1000L

    init {
        startTimer()
    }

    fun onUserInteraction() {
        lastInteractionTime = System.currentTimeMillis()
    }

    private fun startTimer() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                delay(1000)
                if (System.currentTimeMillis() - lastInteractionTime > idleThreshold) {
                    withContext(Dispatchers.Main) { onUserInactive() }
                }
            }
        }
    }
}
