package ui.drawer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import config.UIConfig
import manager.ui.drawer.RightDrawerManager
import utils.BoundsRegulator


object RightDrawerRegulator : BoundsRegulator(
	Direction.Left, manager = RightDrawerManager
) {
	var isActivate by mutableStateOf(false)

	override val onDrag: (Float) -> Boolean = {
		RightDrawerManager.size -= Dp(it)
		if (RightDrawerManager.size < 200.dp) {
			RightDrawerManager.size = 200.dp
			true
		} else if (RightDrawerManager.size > 800.dp) {
			RightDrawerManager.size = 800.dp
			true
		} else false
	}
	override val onDragStart = {
		isActivate = true
	}
	override val onDragEnd = {
		isActivate = false
		UIConfig.rightDrawerWidth = RightDrawerManager.size.value
	}
}