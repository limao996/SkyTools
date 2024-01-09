package manager.ui.drawer

import androidx.compose.runtime.Composable
import ui.drawer.DrawerContentScope

object DrawerManager {
	var leftFirst: (@Composable DrawerContentScope.() -> Unit)?
		get() = LeftDrawerManager.splitA
		set(value) {
			LeftDrawerManager.splitA = value
		}

	var leftLast: (@Composable DrawerContentScope.() -> Unit)?
		get() = LeftDrawerManager.splitB
		set(value) {
			LeftDrawerManager.splitB = value
		}

	var rightFirst: (@Composable DrawerContentScope.() -> Unit)?
		get() = RightDrawerManager.splitA
		set(value) {
			RightDrawerManager.splitA = value
		}

	var rightLast: (@Composable DrawerContentScope.() -> Unit)?
		get() = RightDrawerManager.splitB
		set(value) {
			RightDrawerManager.splitB = value
		}

	var bottomFirst: (@Composable DrawerContentScope.() -> Unit)?
		get() = BottomDrawerManager.splitA
		set(value) {
			BottomDrawerManager.splitA = value
		}

	var bottomLast: (@Composable DrawerContentScope.() -> Unit)?
		get() = BottomDrawerManager.splitB
		set(value) {
			BottomDrawerManager.splitB = value
		}

	fun reset() {
		leftFirst = null
		leftLast = null
		rightFirst = null
		rightLast = null
		bottomFirst = null
		bottomLast = null
	}

	fun load(content: DrawerScope.() -> Unit) {
		reset()
		DrawerScope.content()
	}
}