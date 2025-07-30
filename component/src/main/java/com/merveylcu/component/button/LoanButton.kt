package com.merveylcu.component.button

import android.os.SystemClock
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.merveylcu.component.extension.conditional
import com.merveylcu.core.theme.LocalAppColors
import com.merveylcu.core.theme.LocalAppDimensions
import com.merveylcu.core.theme.LocalAppShapes

private const val DEBOUNCE = 600L

@Composable
fun LoanButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: LoanButtonStyle = LoanButtonStyle.Primary,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        hoveredElevation = 0.dp,
        focusedElevation = 0.dp
    ),
    shape: Shape = LocalAppShapes.current.medium,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    buttonSize: Dp = LocalAppDimensions.current.buttonHeight,
    @DrawableRes iconResId: Int? = null,
    debounceTime: Long = DEBOUNCE,
    isFillMaxWidth: Boolean = true,
    content: @Composable () -> Unit
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    val colors = when (style) {
        LoanButtonStyle.Primary -> ButtonDefaults.primaryButtonColors()
        LoanButtonStyle.Secondary -> ButtonDefaults.secondaryButtonColors()
    }

    val innerBorder = border ?: style.takeIf { it is LoanButtonStyle.Secondary }?.let {
        val borderColor = LocalAppColors.current.primary
        val borderPassiveColor = LocalAppColors.current.passive
        BorderStroke(
            LocalAppDimensions.current.borderWidth,
            if (enabled) borderColor else borderPassiveColor
        )
    }

    val buttonContent = @Composable {
        Button(
            onClick = {
                val now = SystemClock.elapsedRealtime()
                if (now - lastClickTime > debounceTime) {
                    lastClickTime = now
                    onClick()
                }
            },
            modifier = modifier
                .conditional({ isFillMaxWidth }) { Modifier.fillMaxWidth() }
                .then(Modifier.height(buttonSize)),
            enabled = enabled,
            interactionSource = interactionSource,
            elevation = elevation,
            shape = shape,
            border = innerBorder,
            colors = colors,
            contentPadding = contentPadding
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    content()
                }
                iconResId?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = null
                    )
                }
            }
        }
    }

    buttonContent()
}
