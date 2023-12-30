package ui.sidebar

import androidx.compose.runtime.Composable
import manager.ui.drawer.DrawerManager
import manager.ui.sidebar.SideBarManager.SideBarAction
import ui.drawer.DrawerContentScope
import ui.sidebar.SideBarBuilder.Placement

class SideBarScope(val upScope: SideBarBuilder, val self: SideBarAction) {

	val isSelected: Boolean
		get() = upScope.selected == self

	fun setContent(content: (@Composable DrawerContentScope.() -> Unit)?) {
		DrawerManager.apply {
			when (upScope.placement) {
				Placement.LeftFirst -> leftFirst = content
				Placement.LeftLast -> leftLast = content
				Placement.BottomFirst -> bottomFirst = content
				Placement.RightFirst -> rightFirst = content
				Placement.RightLast -> rightLast = content
				Placement.BottomLast -> bottomLast = content
			}
		}
	}

	fun select(content: @Composable DrawerContentScope.() -> Unit) {
		upScope.selected = self
		setContent(content)
	}

	fun cancel() {
		upScope.selected = null
		setContent(null)
	}
}
