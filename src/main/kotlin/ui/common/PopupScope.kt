package ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.HoverOrPressedIndication
import io.kanro.compose.jetbrains.expui.style.LocalFocusAreaColors
import io.kanro.compose.jetbrains.expui.style.LocalMediumTextStyle
import io.kanro.compose.jetbrains.expui.theme.DarkTheme
import io.kanro.compose.jetbrains.expui.theme.LightTheme
import manager.core.ThemeManager

@LayoutScopeMarker
@Immutable
class PopupScope(
	val onDismissRequest: () -> Unit,
)

@Composable
fun PopupScope.MenuItem(
	onClick: () -> Unit,
	enabled: Boolean = true,
	contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp),
	shape: Shape = RoundedCornerShape(3.dp),
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	content: @Composable RowScope.() -> Unit,
) {
	val focused = remember { mutableStateOf(false) }
	val focusedColors = LocalFocusAreaColors.current
	Row(modifier = Modifier.drawWithCache {
		onDrawBehind {
			if (focused.value) {
				val outline = shape.createOutline(size, layoutDirection, this)
				drawOutline(outline, focusedColors.startBackground)
			}
		}
	}.onFocusEvent {
		focused.value = it.isFocused
	}.clickable(
		enabled = enabled, onClick = {
			onClick()
			onDismissRequest()
		}, interactionSource = interactionSource, indication = HoverOrPressedIndication(shape)
	).fillMaxWidth().padding(contentPadding).defaultMinSize(minHeight = 24.dp),
		verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)
	) {
		content()
	}
}

@Composable
fun PopupScope.Divider() {
	HSubDivider(false)
}

@Composable
fun PopupScope.Title(text: String) {
	io.kanro.compose.jetbrains.expui.control.Label(
		text,
		Modifier.fillMaxWidth().padding(horizontal = 8.dp).padding(top = 4.dp),
		color = if (ThemeManager.current.isDark()) DarkTheme.Grey7
		else LightTheme.Grey7,
		style = LocalMediumTextStyle.current
	)
}
