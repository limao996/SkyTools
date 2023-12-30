package ui.common.popup

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.rememberComponentRectPositionProvider
import io.kanro.compose.jetbrains.expui.control.HoverOrPressedIndication
import io.kanro.compose.jetbrains.expui.control.Label
import io.kanro.compose.jetbrains.expui.control.LocalContextMenuColors
import io.kanro.compose.jetbrains.expui.style.LocalFocusAreaColors
import io.kanro.compose.jetbrains.expui.style.LocalMediumTextStyle
import io.kanro.compose.jetbrains.expui.style.areaBackground
import io.kanro.compose.jetbrains.expui.style.areaBorder
import io.kanro.compose.jetbrains.expui.theme.DarkTheme
import io.kanro.compose.jetbrains.expui.theme.LightTheme
import manager.core.ThemeManager
import ui.common.HSubDivider
import ui.common.JBIcon

@LayoutScopeMarker
@Immutable
class PopupScope(
	val rootOnDismissRequest: () -> Unit,
) {
	var hasIcon by mutableStateOf(false)
}

@Composable
fun PopupScope.MenuItem(
	onClick: () -> Unit,
	enabled: Boolean = true,
	autoDismiss: Boolean = true,
	contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp),
	shape: Shape = RoundedCornerShape(3.dp),
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	onFocusEvent: ((Boolean) -> Unit)? = {},
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
		onFocusEvent?.let { it1 -> it1(it.isFocused) }
	}.clickable(
		enabled = enabled, onClick = {
			if (autoDismiss) rootOnDismissRequest()
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
	autoDismiss: Boolean = true,
	contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp),
	shape: Shape = RoundedCornerShape(3.dp),
	onFocusEvent: (Boolean) -> Unit = {},
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	onClick: () -> Unit,
) {
	MenuItem(onClick,
		enabled,
		autoDismiss,
		contentPadding,
		shape,
		interactionSource,
		onFocusEvent,
		{
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
fun PopupScope.CheckMenuItem(
	checked: Boolean,
	label: String,
	message: String? = null,
	enabled: Boolean = true,
	autoDismiss: Boolean = true,
	contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp),
	shape: Shape = RoundedCornerShape(3.dp),
	onFocusEvent: (Boolean) -> Unit = {},
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	onChecked: (Boolean) -> Unit,
) {
	MenuItem(
		if (checked) "icons/check.svg" else null,
		label,
		message,
		enabled,
		autoDismiss,
		contentPadding,
		shape,
		onFocusEvent,
		interactionSource,
	) { onChecked(checked) }
}

@Composable
fun PopupScope.ExtendMenuItem(
	icon: String? = null,
	label: String,
	message: String? = null,
	enabled: Boolean = true,
	contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp),
	shape: Shape = RoundedCornerShape(3.dp),
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	content: @Composable PopupScope.() -> Unit,
) {
	var isOpen by remember { mutableStateOf(false) }
	Row {
		this@ExtendMenuItem.SubPopup(isOpen, onDismissRequest = {
			isOpen = false
		}) {
			content()
		}
		this@ExtendMenuItem.MenuItem({
			isOpen = true
		}, enabled, false, contentPadding, shape, interactionSource, { }, {
			if (message != null) Message(message)
			JBIcon(
				"icons/right.svg", 14.dp, colorFilter = ColorFilter.tint(
					if (ThemeManager.current.isDark()) DarkTheme.Grey7
					else LightTheme.Grey7
				)
			)

		}) {
			if (icon == "") hasIcon = true
			if (icon != null && icon != "") {
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PopupScope.SubPopup(
	isOpen: Boolean,
	minWidth: Dp = 192.dp,
	modifier: Modifier = Modifier,
	onDismissRequest: () -> Unit = {},
	content: @Composable PopupScope.() -> Unit,
) {
	if (!isOpen) return

	val popupScope = this
	val shape = RoundedCornerShape(8.dp)
	var focusManager by mutableStateOf<FocusManager?>(null)
	var inputModeManager by mutableStateOf<InputModeManager?>(null)
	var i = 0
	Popup(
		popupPositionProvider = rememberComponentRectPositionProvider(
			Alignment.TopEnd, Alignment.BottomEnd, DpOffset(0.dp, (-6).dp)
		),
		properties = PopupProperties(
			focusable = true,
		),
		onDismissRequest = { if (++i % 2 == 0) onDismissRequest() },
		onKeyEvent = {
			if (it.type == KeyEventType.KeyDown) {
				when (it.key.nativeKeyCode) {

					java.awt.event.KeyEvent.VK_ESCAPE -> {
						onDismissRequest()
						true
					}

					java.awt.event.KeyEvent.VK_DOWN -> {
						inputModeManager?.requestInputMode(InputMode.Keyboard)
						focusManager?.moveFocus(FocusDirection.Next)
						true
					}

					java.awt.event.KeyEvent.VK_UP -> {
						inputModeManager?.requestInputMode(InputMode.Keyboard)
						focusManager?.moveFocus(FocusDirection.Previous)
						true
					}

					else -> false
				}
			} else {
				false
			}
		},
	) {
		focusManager = LocalFocusManager.current
		inputModeManager = LocalInputModeManager.current
		LocalContextMenuColors.current.provideArea {
			Box(
				modifier = Modifier
					.shadow(12.dp, shape = shape)
					.areaBorder(shape = shape)
					.areaBackground(shape = shape)
					.sizeIn(maxHeight = 600.dp, minWidth = minWidth)
					.padding(6.dp)
					.width(
						Max
					)
					.then(modifier)
			) {
				val scrollState = rememberScrollState()
				Column(
					modifier = Modifier.verticalScroll(scrollState).padding(horizontal = 4.dp),
					verticalArrangement = Arrangement.spacedBy(4.dp),
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					popupScope.content()
				}
				Box(modifier = Modifier.matchParentSize()) {
					VerticalScrollbar(
						rememberScrollbarAdapter(scrollState),
						modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd)
					)
				}
			}
		}
	}
}