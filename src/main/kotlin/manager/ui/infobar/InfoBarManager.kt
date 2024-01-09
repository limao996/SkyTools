package manager.ui.topbar

import androidx.compose.runtime.*
import myWindow
import ui.infobar.InfoBarScope

object InfoBarManager {
	fun init() {
		push {
			RightGroup("Window", -1) {
				var isAlwaysOnTop by remember { mutableStateOf(myWindow.isAlwaysOnTop) }
				ActionButton(
					icon = if (isAlwaysOnTop) "icons/lock.svg" else "icons/unlock.svg",
					tooltip = "窗口置顶",
					message = if (isAlwaysOnTop) "开启" else "关闭",
				) {
					isAlwaysOnTop = !isAlwaysOnTop
					myWindow.isAlwaysOnTop = isAlwaysOnTop
				}
			}
		}
	}

	val left = mutableStateListOf<Group>()
	val center = mutableStateListOf<Group>()
	val right = mutableStateListOf<Group>()

	fun reset() {
		left.clear()
		center.clear()
		right.clear()
		init()
	}

	fun sort() {
		left.sortBy { group -> group.priority }
		center.sortBy { group -> group.priority }
		right.sortByDescending { group -> group.priority }
	}

	fun push(fn: InfoBarManager.() -> Unit) {
		InfoBarManager.fn()
	}

	data class Group(
		val tag: String,
		val priority: Int,
		val content: @Composable() InfoBarScope.() -> Unit,
	)

	fun LeftGroup(
		tag: String,
		priority: Int,
		content: @Composable() InfoBarScope.() -> Unit,
	) {
		left.add(
			Group(
				tag, priority, content
			)
		)
	}

	fun CenterGroup(
		tag: String,
		priority: Int,
		content: @Composable() InfoBarScope.() -> Unit,
	) {
		center.add(
			Group(
				tag, priority, content
			)
		)
	}

	fun RightGroup(
		tag: String,
		priority: Int,
		content: @Composable() InfoBarScope.() -> Unit,
	) {
		right.add(
			Group(
				tag, priority, content
			)
		)
	}
}
