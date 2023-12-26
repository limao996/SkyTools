package ui.drawer

import androidx.compose.runtime.*
import config.UIConfig
import manager.ui.drawer.BottomDrawerManager
import utils.BoundsRegulator

object BottomDrawerSplitRegulator : BoundsRegulator(
	Direction.Right,
) {
	var isActivate by mutableStateOf(false)

	override val onDrag: (Float) -> Boolean = {
		BottomDrawerManager.splitWeight += (it / BottomDrawerRegulator.bounds.width) * 2
		if (BottomDrawerManager.splitWeight < -0.8) {
			BottomDrawerManager.splitWeight = -0.8F
			true
		} else if (BottomDrawerManager.splitWeight > 0.8) {
			BottomDrawerManager.splitWeight = 0.8F
			true
		} else false
	}
	override val onDragStart = {
		isActivate = true
	}
	override val onDragEnd = {
		isActivate = false
		UIConfig.bottomDrawerSplitWeight = BottomDrawerManager.splitWeight
	}
}
