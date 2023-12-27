package manager.ui.drawer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import config.UIConfig
import ui.drawer.DrawerContentScope

object LeftDrawerManager : BaseSubDrawerManager() {
	override var size by mutableStateOf(Dp(UIConfig.leftDrawerWidth))
	override var splitWeight by mutableStateOf(UIConfig.leftDrawerSplitWeight)
	override var splitA by mutableStateOf<(@Composable DrawerContentScope.() -> Unit)?>(
		null
	)
	override var splitB by mutableStateOf<(@Composable DrawerContentScope.() -> Unit)?>(
		null
	)
}