package manager.ui.drawer

import androidx.compose.runtime.Composable
import ui.drawer.DrawerContentScope

abstract class BaseSubDrawerManager {
	abstract var splitA: (@Composable DrawerContentScope.() -> Unit)?
	abstract var splitB: (@Composable DrawerContentScope.() -> Unit)?

	val isShow: Boolean
		get() = splitA != null || splitB != null

}