package ui.drawer

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import config.UIConfig
import manager.ui.drawer.RightDrawerManager
import utils.BoundsRegulator


object RightDrawerRegulator : BoundsRegulator(
	Direction.Left,
) {
	var isActivate by mutableStateOf(false)

	override val onDrag: (Float) -> Boolean = {
		RightDrawerManager.width -= Dp(it)
		if (RightDrawerManager.width < 200.dp) {
			RightDrawerManager.width = 200.dp
			true
		} else if (RightDrawerManager.width > 800.dp) {
			RightDrawerManager.width = 800.dp
			true
		} else false
	}
	override val onDragStart = {
		isActivate = true
	}
	override val onDragEnd = {
		isActivate = false
		UIConfig.rightDrawerWidth = RightDrawerManager.width.value
	}
}