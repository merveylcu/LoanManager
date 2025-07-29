package com.merveylcu.core.navigation

import androidx.navigation.NavBackStackEntry

const val SCREEN_ARGS_KEY = "__SCREEN_ARGS__"
const val BACK_STACK_ENTRY_ARGS_KEY = "__BACK_STACK_ENTRY_ARGS__"

inline fun <reified T> NavBackStackEntry.getBackStackEntryArgs(): T? {
    val args = savedStateHandle.get<Any?>(BACK_STACK_ENTRY_ARGS_KEY)

    return args.takeIf { it is T }?.let {
        savedStateHandle.remove<T>(BACK_STACK_ENTRY_ARGS_KEY)
    }
}
