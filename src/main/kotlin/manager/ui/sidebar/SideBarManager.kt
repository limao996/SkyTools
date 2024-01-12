package manager.ui.sidebar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import ui.drawer.DividerMode.Show
import ui.drawer.DrawerPane
import ui.drawer.ToolBar
import ui.sidebar.SideBarBuilder
import ui.sidebar.SideBarBuilder.Placement

object SideBarManager {

	fun init() {

		push {
			LeftFirst {
				Action("", -1, "icons/github.svg", "测试") {
					if (!isSelected) select {
						DrawerPane(toolbar = { ToolBar("测试") })
					} else cancel()
				}
			}
			LeftLast {
				Action("", -1, "icons/github.svg", "测试") {
					if (!isSelected) select {
						DrawerPane(toolbar = { ToolBar("测试") })
					} else cancel()
				}
			}
			RightFirst {
				Action("", -1, "icons/github.svg", "测试") {
					if (!isSelected) select {
						DrawerPane(toolbar = { ToolBar("测试") })
					} else cancel()
				}
			}
			RightLast {
				Action("", -1, "icons/github.svg", "测试") {
					if (!isSelected) select {
						DrawerPane(toolbar = { ToolBar("测试") })
					} else cancel()
				}
			}
			BottomFirst {
				Action("", -1, "icons/github.svg", "测试") {
					if (!isSelected) select {
						DrawerPane(dividerMode = Show, toolbar = { ToolBar("测试") })
					} else cancel()
				}
			}
			BottomLast {
				Action("", -1, "icons/github.svg", "测试") {
					if (!isSelected) select {
						DrawerPane(dividerMode = Show, toolbar = { ToolBar("测试") })
					} else cancel()
				}
			}
		}
	}

	val leftFirst = mutableStateListOf<SideBarAction>()
	val leftLast = mutableStateListOf<SideBarAction>()
	val bottomFirst = mutableStateListOf<SideBarAction>()
	val rightFirst = mutableStateListOf<SideBarAction>()
	val rightLast = mutableStateListOf<SideBarAction>()
	val bottomLast = mutableStateListOf<SideBarAction>()

	val LeftFirstBuilder = SideBarBuilder(Placement.LeftFirst)
	val LeftLastBuilder = SideBarBuilder(Placement.LeftLast)
	val BottomFirstBuilder = SideBarBuilder(Placement.BottomFirst)
	val RightFirstBuilder = SideBarBuilder(Placement.RightFirst)
	val RightLastBuilder = SideBarBuilder(Placement.RightLast)
	val BottomLastBuilder = SideBarBuilder(Placement.BottomLast)

	fun reset() {
		leftFirst.clear()
		leftLast.clear()
		bottomFirst.clear()
		rightFirst.clear()
		rightLast.clear()
		bottomLast.clear()
		init()
	}

	fun sortLeftBar() {
		leftFirst.sortBy { group -> group.priority }
		leftLast.sortBy { group -> group.priority }
		bottomFirst.sortBy { group -> group.priority }
	}

	fun sortRightBar() {
		rightFirst.sortBy { group -> group.priority }
		rightLast.sortBy { group -> group.priority }
		bottomLast.sortBy { group -> group.priority }
	}

	fun sort() {
		sortLeftBar()
		sortRightBar()
	}

	fun push(fn: SideBarManager.() -> Unit) {
		SideBarManager.fn()
	}


	data class SideBarAction(
		val tag: String,
		val priority: Int,
		val content: @Composable() SideBarBuilder.() -> Unit,
	)

	fun LeftFirst(content: SideBarBuilder.() -> Unit) = LeftFirstBuilder.content()


	fun LeftLast(content: SideBarBuilder.() -> Unit) = LeftLastBuilder.content()


	fun RightFirst(content: SideBarBuilder.() -> Unit) = RightFirstBuilder.content()


	fun RightLast(content: SideBarBuilder.() -> Unit) = RightLastBuilder.content()


	fun BottomFirst(content: SideBarBuilder.() -> Unit) = BottomFirstBuilder.content()


	fun BottomLast(content: SideBarBuilder.() -> Unit) = BottomLastBuilder.content()

}