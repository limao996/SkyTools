package manager.ui.drawer

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import config.UIConfig
import ui.drawer.DrawerContentScope

object RightDrawerManager : BaseSubDrawerManager() {
	var width by mutableStateOf(Dp(UIConfig.rightDrawerWidth))
	var splitWeight by mutableStateOf(UIConfig.rightDrawerSplitWeight)
	override var splitA by mutableStateOf<(@Composable DrawerContentScope.() -> Unit)?>(null)
	override var splitB by mutableStateOf<(@Composable DrawerContentScope.() -> Unit)?>(null)
}