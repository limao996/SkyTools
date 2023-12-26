package ui.drawer

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import config.UIConfig
import manager.ui.drawer.LeftDrawerManager
import utils.BoundsRegulator


object LeftDrawerRegulator : BoundsRegulator(
	Direction.Right,
) {
	var isActivate by mutableStateOf(false)

	override val onDrag: (Float) -> Boolean = {
		LeftDrawerManager.width += Dp(it)
		if (LeftDrawerManager.width < 200.dp) {
			LeftDrawerManager.width = 200.dp
			true
		} else if (LeftDrawerManager.width > 800.dp) {
			LeftDrawerManager.width = 800.dp
			true
		} else false
	}
	override val onDragStart = {
		isActivate = true
	}
	override val onDragEnd = {
		isActivate = false
		UIConfig.leftDrawerWidth = LeftDrawerManager.width.value
	}
}
