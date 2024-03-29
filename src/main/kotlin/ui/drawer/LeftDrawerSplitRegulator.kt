package ui.drawer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import config.UIConfig
import manager.ui.drawer.LeftDrawerManager
import utils.BoundsRegulator

object LeftDrawerSplitRegulator : BoundsRegulator(
	Direction.Bottom, manager = LeftDrawerManager
) {
	var isActivate by mutableStateOf(false)

	override val onDrag: (Float) -> Boolean = {
		LeftDrawerManager.splitWeight += (it / LeftDrawerRegulator.bounds.height) * 2
		if (LeftDrawerManager.splitWeight < -0.6) {
			LeftDrawerManager.splitWeight = -0.6F
			true
		} else if (LeftDrawerManager.splitWeight > 0.6) {
			LeftDrawerManager.splitWeight = 0.6F
			true
		} else false
	}
	override val onDragStart = {
		isActivate = true
	}
	override val onDragEnd = {
		isActivate = false
		UIConfig.leftDrawerSplitWeight = LeftDrawerManager.splitWeight
	}
}

