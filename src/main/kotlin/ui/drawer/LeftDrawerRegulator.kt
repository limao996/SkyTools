package ui.drawer

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import component
import config.UIConfig
import manager.ui.drawer.LeftDrawerManager
import java.awt.Cursor


var leftDrawerBounds by mutableStateOf(Rect.Zero)

object LeftDrawerRegulator {
	private var isHover = false
	var lock by mutableStateOf(false)
	private var lateState = false
	private var isAdjusting = false

	fun drag(dragAmount: Float) {
		if (lock) {
			LeftDrawerManager.width += Dp(dragAmount)
			if (LeftDrawerManager.width < 200.dp) LeftDrawerManager.width = 200.dp
			if (LeftDrawerManager.width > 800.dp) LeftDrawerManager.width = 800.dp
		}
	}

	fun hover(event: PointerEvent) {
		val pos = event.changes.first().position
		isHover = false
		if (pos.x > leftDrawerBounds.right + 1.dp.value - 4.dp.value && pos.x < leftDrawerBounds.right + 1.dp.value + 4.dp.value) {
			isHover = true
			if (event.type == PointerEventType.Press) lock = true
		}
		if (event.type == PointerEventType.Release) {
			lock = false
			UIConfig.leftDrawerWidth = LeftDrawerManager.width.value
		}
		isAdjusting = isHover || lock

		if (lateState != isAdjusting) {
			if (isAdjusting) {
				component.cursor = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR)
			} else {
				component.cursor = Cursor.getDefaultCursor()
			}
		}
		lateState = isAdjusting
	}
}
