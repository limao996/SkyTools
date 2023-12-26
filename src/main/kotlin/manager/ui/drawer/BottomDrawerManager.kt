package manager.ui.drawer

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import config.UIConfig
import ui.drawer.DrawerContentScope

object BottomDrawerManager : BaseSubDrawerManager() {
	var height by mutableStateOf(Dp(UIConfig.bottomDrawerHeight))
	var splitWeight by mutableStateOf(UIConfig.bottomDrawerSplitWeight)
	override var splitA by mutableStateOf<(@Composable DrawerContentScope.() -> Unit)?>(null)
	override var splitB by mutableStateOf<(@Composable DrawerContentScope.() -> Unit)?>(null)
}