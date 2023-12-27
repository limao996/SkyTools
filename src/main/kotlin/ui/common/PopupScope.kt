package ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.HoverOrPressedIndication
import io.kanro.compose.jetbrains.expui.control.Label
import io.kanro.compose.jetbrains.expui.style.LocalFocusAreaColors
import io.kanro.compose.jetbrains.expui.style.LocalMediumTextStyle
import io.kanro.compose.jetbrains.expui.theme.DarkTheme
import io.kanro.compose.jetbrains.expui.theme.LightTheme
import manager.core.ThemeManager

@LayoutScopeMarker
@Immutable
class PopupScope(
	val onDismissRequest: () -> Unit,
) {
	var hasIcon = false
}

@Composable
fun PopupScope.MenuItem(
	onClick: () -> Unit,
	enabled: Boolean = true,
	contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp),
	shape: Shape = RoundedCornerShape(3.dp),
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	message: (@Composable PopupScope.() -> Unit) = {},
	content: @Composable PopupScope.() -> Unit,
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
			onDismissRequest()
			onClick()
		}, interactionSource = interactionSource, indication = HoverOrPressedIndication(shape)
	).fillMaxWidth().padding(contentPadding).defaultMinSize(minHeight = 24.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(6.dp)
	) {
		this@MenuItem.content()
		Spacer(Modifier.weight(1F))
		this@MenuItem.message()
	}
}

@Composable
fun PopupScope.MenuItem(
	icon: String? = null,
	label: String,
	message: String? = null,
	enabled: Boolean = true,
	contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp),
	shape: Shape = RoundedCornerShape(3.dp),
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	onClick: () -> Unit,
) {
	MenuItem(onClick, enabled, contentPadding, shape, interactionSource, {
		if (message != null) Message(message)
	}) {
		if (icon != null) {
			hasIcon = true
			JBIcon(
				icon, 16.dp
			)
		} else if (hasIcon) Spacer(Modifier.size(16.dp))
		if (enabled) Label(label)
		else Label(
			label, color = if (ThemeManager.current.isDark()) DarkTheme.Grey6
			else LightTheme.Grey6
		)
	}
}


@Composable
fun PopupScope.Divider() {
	HSubDivider(false)
}

@Composable
fun PopupScope.Title(text: String) {
	Label(
		text,
		Modifier.fillMaxWidth().padding(horizontal = 8.dp).padding(top = 4.dp),
		color = if (ThemeManager.current.isDark()) DarkTheme.Grey7
		else LightTheme.Grey7,
		style = LocalMediumTextStyle.current
	)
}

@Composable
fun PopupScope.Message(text: String) {
	Label(
		text, color = if (ThemeManager.current.isDark()) DarkTheme.Grey7
		else LightTheme.Grey7, style = LocalMediumTextStyle.current
	)
}
