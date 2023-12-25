package manager.ui.drawer

import androidx.compose.runtime.Composable

object DrawerManager {
	var leftFirst: (@Composable DrawerScope.() -> Unit)?
		get() = LeftDrawerManager.splitA
		set(value) {
			LeftDrawerManager.splitA = value
		}

	var leftLast: (@Composable DrawerScope.() -> Unit)?
		get() = LeftDrawerManager.splitB
		set(value) {
			LeftDrawerManager.splitB = value
		}

	var rightFirst: (@Composable DrawerScope.() -> Unit)?
		get() = RightDrawerManager.splitA
		set(value) {
			RightDrawerManager.splitA = value
		}

	var rightLast: (@Composable DrawerScope.() -> Unit)?
		get() = RightDrawerManager.splitB
		set(value) {
			RightDrawerManager.splitB = value
		}

	var bottomFirst: (@Composable DrawerScope.() -> Unit)?
		get() = BottomDrawerManager.splitA
		set(value) {
			BottomDrawerManager.splitA = value
		}

	var bottomLast: (@Composable DrawerScope.() -> Unit)?
		get() = BottomDrawerManager.splitB
		set(value) {
			BottomDrawerManager.splitB = value
		}
}