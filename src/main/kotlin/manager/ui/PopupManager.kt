package manager.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.common.popup.JBPopup
import ui.common.popup.PopupScope

object PopupManager {
	private var state by mutableStateOf(false)
	private var minWidth: Dp = 192.dp
	private var focusable: Boolean = true
	private var useCursorPosition: Boolean = true
	private var modifier: Modifier = Modifier
	private var onDismissRequest: () -> Unit = {}
	private var content: @Composable() (PopupScope.() -> Unit) = {}

	@Composable
	fun show() {
		JBPopup(
			state,
			minWidth,
			focusable,
			useCursorPosition,
			modifier,
			{
				state = false
				onDismissRequest()
			},
			content,
		)
	}

	fun open(
		onDismissRequest: () -> Unit = {},
		minWidth: Dp = 192.dp,
		focusable: Boolean = true,
		useCursorPosition: Boolean = true,
		modifier: Modifier = Modifier,
		content: @Composable() (PopupScope.() -> Unit),
	) {
		state = true
		this.minWidth = minWidth
		this.focusable = focusable
		this.useCursorPosition = useCursorPosition
		this.modifier = modifier
		this.onDismissRequest = onDismissRequest
		this.content = content
	}
}