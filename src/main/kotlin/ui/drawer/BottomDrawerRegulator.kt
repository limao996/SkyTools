package ui.drawer

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import component
import config.UIConfig
import manager.ui.drawer.BottomDrawerManager
import java.awt.Cursor


var bottomDrawerBounds by mutableStateOf(Rect.Zero)

object BottomDrawerRegulator {
	private var isHover = false
	var lock by mutableStateOf(false)
	private var lateState = false
	private var isAdjusting = false

	fun drag(dragAmount: Float) {
		if (lock) {
			BottomDrawerManager.height -= Dp(dragAmount)
			if (BottomDrawerManager.height < 150.dp) BottomDrawerManager.height = 150.dp
			if (BottomDrawerManager.height > 500.dp) BottomDrawerManager.height = 500.dp
		}
	}

	fun hover(event: PointerEvent) {
		val pos = event.changes.first().position
		isHover = false
		if (pos.y > bottomDrawerBounds.top - 1.dp.value - 4.dp.value && pos.y < bottomDrawerBounds.top - 1.dp.value + 4.dp.value) {
			isHover = true
			if (event.type == PointerEventType.Press) lock = true
		}
		if (event.type == PointerEventType.Release) {
			lock = false
			UIConfig.bottomDrawerHeight = BottomDrawerManager.height.value
		}
		isAdjusting = isHover || lock

		if (lateState != isAdjusting) {
			if (isAdjusting) {
				component.cursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR)
			} else {
				component.cursor = Cursor.getDefaultCursor()
			}
		}
		lateState = isAdjusting
	}
}