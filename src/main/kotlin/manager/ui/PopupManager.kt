package manager.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.common.JBPopup
import ui.common.PopupScope

object PopupManager {
	var state by mutableStateOf(false)
	private var onDismissRequest: () -> Unit = {}
	private var minWidth: Dp = 192.dp
	private var onPreviewKeyEvent: (KeyEvent) -> Boolean = { false }
	private var onKeyEvent: (KeyEvent) -> Boolean = { false }
	private var modifier: Modifier = Modifier
	private var content: @Composable() (PopupScope.() -> Unit) = {}

	@Composable
	fun show() {
		JBPopup(
			state,
			{
				state = false
				onDismissRequest()
			},
			minWidth,
			onPreviewKeyEvent,
			onKeyEvent,
			modifier,
			content,
		)
	}

	fun open(
		onDismissRequest: () -> Unit = {},
		minWidth: Dp = 192.dp,
		onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
		onKeyEvent: (KeyEvent) -> Boolean = { false },
		modifier: Modifier = Modifier,
		content: @Composable() (PopupScope.() -> Unit),
	) {
		this.onDismissRequest = onDismissRequest
		this.minWidth = minWidth
		this.onPreviewKeyEvent = onPreviewKeyEvent
		this.onKeyEvent = onKeyEvent
		this.modifier = modifier
		this.content = content
		this.state = true
	}
}