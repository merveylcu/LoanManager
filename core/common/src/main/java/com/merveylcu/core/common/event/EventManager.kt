package com.merveylcu.core.common.event

import javax.inject.Inject

class EventManager @Inject constructor(private val events: List<EventTracker>) {

    fun logEvent() {
        events.forEach { item -> item.trackEvent() }
    }
}