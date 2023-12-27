package config

import manager.core.ThemeManager
import manager.ui.drawer.BottomDrawerManager
import manager.ui.drawer.LeftDrawerManager
import manager.ui.drawer.RightDrawerManager
import utils.MapDB

object UIConfig : MapDB("UIConfig") {
	override val autoCommit = true

	var theme by Node { ThemeManager.System }
	var leftDrawerWidth by Node { LeftDrawerManager.defaultSize.value }
	var leftDrawerSplitWeight by Node { LeftDrawerManager.defaultSplitWeight }
	var rightDrawerWidth by Node { RightDrawerManager.defaultSize.value }
	var rightDrawerSplitWeight by Node { RightDrawerManager.defaultSplitWeight }
	var bottomDrawerHeight by Node { BottomDrawerManager.defaultSize.value }
	var bottomDrawerSplitWeight by Node { BottomDrawerManager.defaultSplitWeight }
}