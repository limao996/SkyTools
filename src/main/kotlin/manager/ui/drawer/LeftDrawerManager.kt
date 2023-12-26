package manager.ui.drawer

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import config.UIConfig
import ui.drawer.DrawerContentScope

object LeftDrawerManager : BaseSubDrawerManager() {
	var width by mutableStateOf(Dp(UIConfig.leftDrawerWidth))
	var splitWeight by mutableStateOf(UIConfig.leftDrawerSplitWeight)
	override var splitA by mutableStateOf<(@Composable DrawerContentScope.() -> Unit)?>(
		null
	)
	override var splitB by mutableStateOf<(@Composable DrawerContentScope.() -> Unit)?>(
		null
	)
}