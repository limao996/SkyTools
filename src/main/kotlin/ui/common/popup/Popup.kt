package ui.common.popup

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
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
import androidx.compose.ui.window.rememberCursorPositionProvider
import io.kanro.compose.jetbrains.expui.control.LocalContextMenuColors
import io.kanro.compose.jetbrains.expui.style.areaBackground
import io.kanro.compose.jetbrains.expui.style.areaBorder

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun JBPopup(
	isOpen: Boolean,
	minWidth: Dp = 192.dp,
	focusable: Boolean = true,
	useCursorPosition: Boolean = true,
	modifier: Modifier = Modifier,
	onDismissRequest: () -> Unit = {},
	content: @Composable PopupScope.() -> Unit,
) {
	if (!isOpen) return

	val popupScope = remember { PopupScope(onDismissRequest) }
	val shape = RoundedCornerShape(8.dp)
	var focusManager by mutableStateOf<FocusManager?>(null)
	var inputModeManager by mutableStateOf<InputModeManager?>(null)
	var i = 0
	Popup(
		popupPositionProvider = if (useCursorPosition) rememberCursorPositionProvider(
			DpOffset(
				8.dp, 8.dp
			)
		) else rememberComponentRectPositionProvider(
			Alignment.TopEnd, Alignment.BottomEnd, DpOffset(0.dp, (-6).dp)
		),
		properties = PopupProperties(
			focusable = focusable,
			dismissOnBackPress = focusable,
			dismissOnClickOutside = focusable,
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

