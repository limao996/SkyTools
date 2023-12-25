package ui.drawer

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.unit.dp
import component
import config.UIConfig
import manager.ui.drawer.BottomDrawerManager
import java.awt.Cursor


var bottomSplitDrawerBounds by mutableStateOf(Rect.Zero)

object BottomDrawerSplitRegulator {
	private var isHover = false
	var lock by mutableStateOf(false)
	private var lateState = false
	private var isAdjusting = false

	fun drag(dragAmount: Float) {
		if (lock) {
			BottomDrawerManager.splitWeight += (dragAmount / bottomDrawerBounds.width) * 2

			if (BottomDrawerManager.splitWeight < -0.8) BottomDrawerManager.splitWeight = -0.8F
			if (BottomDrawerManager.splitWeight > 0.8) BottomDrawerManager.splitWeight = 0.8F
		}
	}

	fun hover(event: PointerEvent) {
		val pos = event.changes.first().position
		isHover = false
		if (pos.x > bottomSplitDrawerBounds.center.x - 4.dp.value && pos.x < bottomSplitDrawerBounds.center.x + 4.dp.value) {
			isHover = true
			if (event.type == PointerEventType.Press) lock = true
		}
		if (event.type == PointerEventType.Release) {
			lock = false
			UIConfig.bottomDrawerSplitWeight = BottomDrawerManager.splitWeight
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