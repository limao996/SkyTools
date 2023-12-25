package manager.ui.drawer

import androidx.compose.runtime.Composable

abstract class BaseSubDrawerManager {
	abstract var splitA: (@Composable DrawerScope.() -> Unit)?
	abstract var splitB: (@Composable DrawerScope.() -> Unit)?

	val isShow: Boolean
		get() = splitA != null || splitB != null

}