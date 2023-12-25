package config

import androidx.compose.ui.unit.dp
import manager.core.ThemeManager
import utils.MapDB

object UIConfig : MapDB("UIConfig") {
	override val autoCommit = true

	var theme by Node { ThemeManager.Dark }
	var leftDrawerWidth by Node { 400.dp.value }
	var leftDrawerSplitWeight by Node { 0F }
	var rightDrawerWidth by Node { 500.dp.value }
	var rightDrawerSplitWeight by Node { 0F }
	var bottomDrawerHeight by Node { 350.dp.value }
	var bottomDrawerSplitWeight by Node { 0F }
}