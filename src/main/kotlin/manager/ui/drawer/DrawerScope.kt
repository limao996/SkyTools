package manager.ui.drawer

import androidx.compose.runtime.Composable
import ui.drawer.DrawerContentScope

object DrawerScope {
	fun LeftLast(content: @Composable DrawerContentScope.() -> Unit) {
		DrawerManager.leftLast = content
	}

	fun LeftFirst(content: @Composable DrawerContentScope.() -> Unit) {
		DrawerManager.leftFirst = content
	}

	fun RightLast(content: @Composable DrawerContentScope.() -> Unit) {
		DrawerManager.rightLast = content
	}

	fun RightFirst(content: @Composable DrawerContentScope.() -> Unit) {
		DrawerManager.rightFirst = content
	}

	fun BottomLast(content: @Composable DrawerContentScope.() -> Unit) {
		DrawerManager.bottomLast = content
	}

	fun BottomFirst(content: @Composable DrawerContentScope.() -> Unit) {
		DrawerManager.bottomFirst = content
	}
}