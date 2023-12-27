package manager.ui.drawer

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.drawer.DrawerContentScope

abstract class BaseSubDrawerManager {
	abstract var size: Dp
	abstract var splitWeight: Float
	abstract var splitA: (@Composable DrawerContentScope.() -> Unit)?
	abstract var splitB: (@Composable DrawerContentScope.() -> Unit)?

	open val defaultSize = 400.dp
	open val defaultSplitWeight = 0F

	val isShow: Boolean
		get() = splitA != null || splitB != null
}