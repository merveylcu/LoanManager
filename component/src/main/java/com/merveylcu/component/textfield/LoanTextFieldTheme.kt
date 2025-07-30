package com.merveylcu.component.textfield

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.materialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.merveylcu.core.theme.LocalAppColors
import com.merveylcu.core.theme.LocalAppTextSizes

@Composable
fun LoanTextFieldTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme,
        typography = MaterialTheme.typography.copy(
            bodySmall = MaterialTheme.typography.bodySmall.copy(
                fontSize = LocalAppTextSizes.current.small,
                fontWeight = FontWeight.Normal
            ),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(
                fontSize = LocalAppTextSizes.current.large,
                fontWeight = FontWeight.Normal
            )
        ),
        content = content
    )
}

@Composable
internal fun loanTextFieldColors(hasDisabledBackground: Boolean): TextFieldColors {
    val errorColor = LocalAppColors.current.error
    val labelColor = LocalAppColors.current.passive
    val textColor = LocalAppColors.current.textPrimary
    val cursorColor = LocalAppColors.current.primary
    val disabledContainerColor = if (hasDisabledBackground) {
        LocalAppColors.current.passive
    } else {
        Color.Transparent
    }

    return TextFieldDefaults.colors(
        focusedTextColor = textColor,
        unfocusedTextColor = textColor,
        disabledTextColor = textColor,
        errorTextColor = textColor,

        unfocusedSupportingTextColor = textColor,
        focusedSupportingTextColor = textColor,
        disabledSupportingTextColor = textColor,
        errorSupportingTextColor = textColor,

        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = disabledContainerColor,
        errorContainerColor = Color.Transparent,

        focusedLabelColor = labelColor,
        unfocusedLabelColor = labelColor,
        disabledLabelColor = labelColor,
        errorLabelColor = errorColor,

        cursorColor = cursorColor,
        errorCursorColor = cursorColor,

        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,

        selectionColors = TextSelectionColors(
            handleColor = cursorColor,
            backgroundColor = LocalAppColors.current.passive
        )
    )
}

@Composable
internal fun loanTextFieldBorderColor(
    isError: Boolean,
    isFocus: Boolean,
    hasDisabledBackground: Boolean,
    isBorderless: Boolean = false
): State<Color> {
    val errorColor = LocalAppColors.current.error
    val defaultBorderColor = LocalAppColors.current.passive
    val focusedBorderColor = LocalAppColors.current.active
    val disabledBorderColor = LocalAppColors.current.active
    val borderlessColor = LocalAppColors.current.transparent

    val colorValue = when {
        isBorderless -> borderlessColor
        isError -> errorColor
        isFocus -> focusedBorderColor
        hasDisabledBackground -> disabledBorderColor
        else -> defaultBorderColor
    }
    return rememberUpdatedState(colorValue)
}
