package ui.drawer

import androidx.compose.runtime.*
import config.UIConfig
import manager.ui.drawer.RightDrawerManager
import utils.BoundsRegulator

object RightDrawerSplitRegulator : BoundsRegulator(
	Direction.Bottom,
) {
	var isActivate by mutableStateOf(false)

	override val onDrag: (Float) -> Boolean = {
		RightDrawerManager.splitWeight += (it / RightDrawerRegulator.bounds.height) * 2
		if (RightDrawerManager.splitWeight < -0.6) {
			RightDrawerManager.splitWeight = -0.6F
			true
		} else if (RightDrawerManager.splitWeight > 0.6) {
			RightDrawerManager.splitWeight = 0.6F
			true
		} else false
	}
	override val onDragStart = {
		isActivate = true
	}
	override val onDragEnd = {
		isActivate = false
		UIConfig.rightDrawerSplitWeight = RightDrawerManager.splitWeight
	}
}

