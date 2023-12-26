package ui.drawer

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import config.UIConfig
import manager.ui.drawer.BottomDrawerManager
import utils.BoundsRegulator

object BottomDrawerRegulator : BoundsRegulator(
	Direction.Top,
) {
	var isActivate by mutableStateOf(false)

	override val onDrag: (Float) -> Boolean = {
		BottomDrawerManager.height -= Dp(it)

		if (BottomDrawerManager.height < 150.dp) {
			BottomDrawerManager.height = 150.dp
			true
		} else if (BottomDrawerManager.height > 500.dp) {
			BottomDrawerManager.height = 500.dp
			true
		} else false
	}
	override val onDragStart = {
		isActivate = true
	}
	override val onDragEnd = {
		isActivate = false
		UIConfig.bottomDrawerHeight = BottomDrawerManager.height.value
	}
}
