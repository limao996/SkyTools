package ui.drawer

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.unit.dp
import component
import config.UIConfig
import manager.ui.drawer.LeftDrawerManager
import java.awt.Cursor


var leftSplitDrawerBounds by mutableStateOf(Rect.Zero)

object LeftDrawerSplitRegulator {
	private var isHover = false
	var lock by mutableStateOf(false)
	private var lateState = false
	private var isAdjusting = false
	fun drag(dragAmount: Float) {
		if (lock) {
			LeftDrawerManager.splitWeight += (dragAmount / leftDrawerBounds.height) * 2

			if (LeftDrawerManager.splitWeight < -0.6) LeftDrawerManager.splitWeight = -0.6F
			if (LeftDrawerManager.splitWeight > 0.6) LeftDrawerManager.splitWeight = 0.6F
		}
	}

	fun hover(event: PointerEvent) {
		val pos = event.changes.first().position
		isHover = false
		if (pos.y > leftSplitDrawerBounds.center.y - 4.dp.value && pos.y < leftSplitDrawerBounds.center.y + 4.dp.value) {
			isHover = true
			if (event.type == PointerEventType.Press) lock = true
		}
		if (event.type == PointerEventType.Release) {
			lock = false
			UIConfig.leftDrawerSplitWeight = LeftDrawerManager.splitWeight
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

