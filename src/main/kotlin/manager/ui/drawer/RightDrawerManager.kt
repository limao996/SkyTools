package manager.ui.drawer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import config.UIConfig
import ui.drawer.DrawerContentScope

object RightDrawerManager : BaseSubDrawerManager() {
	override val defaultSize = 500.dp
	override var size by mutableStateOf(Dp(UIConfig.rightDrawerWidth))
	override var splitWeight by mutableStateOf(UIConfig.rightDrawerSplitWeight)
	override var splitA by mutableStateOf<(@Composable DrawerContentScope.() -> Unit)?>(null)
	override var splitB by mutableStateOf<(@Composable DrawerContentScope.() -> Unit)?>(null)
}