package ui.drawer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import config.UIConfig
import manager.ui.drawer.BottomDrawerManager
import utils.BoundsRegulator

object BottomDrawerRegulator : BoundsRegulator(
	Direction.Top, manager = BottomDrawerManager
) {
	var isActivate by mutableStateOf(false)

	override val onDrag: (Float) -> Boolean = {
		BottomDrawerManager.size -= Dp(it)

		if (BottomDrawerManager.size < 150.dp) {
			BottomDrawerManager.size = 150.dp
			true
		} else if (BottomDrawerManager.size > 500.dp) {
			BottomDrawerManager.size = 500.dp
			true
		} else false
	}
	override val onDragStart = {
		isActivate = true
	}
	override val onDragEnd = {
		isActivate = false
		UIConfig.bottomDrawerHeight = BottomDrawerManager.size.value
	}
}
