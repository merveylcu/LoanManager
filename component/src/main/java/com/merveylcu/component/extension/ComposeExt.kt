package com.merveylcu.component.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.conditional(predicate: () -> Boolean, block: @Composable () -> Modifier): Modifier {
    return if (predicate()) {
        then(block())
    } else {
        this
    }
}
