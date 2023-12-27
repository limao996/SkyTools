package ui.drawer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import config.UIConfig
import manager.ui.drawer.LeftDrawerManager
import utils.BoundsRegulator


object LeftDrawerRegulator : BoundsRegulator(
	Direction.Right, manager = LeftDrawerManager
) {
	var isActivate by mutableStateOf(false)

	override val onDrag: (Float) -> Boolean = {
		LeftDrawerManager.size += Dp(it)
		if (LeftDrawerManager.size < 200.dp) {
			LeftDrawerManager.size = 200.dp
			true
		} else if (LeftDrawerManager.size > 800.dp) {
			LeftDrawerManager.size = 800.dp
			true
		} else false
	}
	override val onDragStart = {
		isActivate = true
	}
	override val onDragEnd = {
		isActivate = false
		UIConfig.leftDrawerWidth = LeftDrawerManager.size.value
	}
}
