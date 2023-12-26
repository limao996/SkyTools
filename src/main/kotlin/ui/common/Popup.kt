package ui.common

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import io.kanro.compose.jetbrains.expui.control.LocalContextMenuColors
import io.kanro.compose.jetbrains.expui.style.areaBackground
import io.kanro.compose.jetbrains.expui.style.areaBorder

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun JBPopup(
	isOpen: Boolean,
	onDismissRequest: () -> Unit = {},
	minWidth: Dp = 192.dp,
	onPreviewKeyEvent: ((KeyEvent) -> Boolean) = { false },
	onKeyEvent: ((KeyEvent) -> Boolean) = { false },
	modifier: Modifier = Modifier,
	content: @Composable PopupScope.() -> Unit,
) {
	if (!isOpen) return

	val popupScope = remember { PopupScope(onDismissRequest) }
	val shape = RoundedCornerShape(8.dp)
	var focusManager by mutableStateOf<FocusManager?>(null)
	var inputModeManager by mutableStateOf<InputModeManager?>(null)

	Popup(
		popupPositionProvider = rememberCursorPositionProvider(DpOffset(16.dp, 16.dp)),
		properties = PopupProperties(focusable = true),
		onDismissRequest = onDismissRequest,
		onPreviewKeyEvent = onPreviewKeyEvent,
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
						IntrinsicSize.Max
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