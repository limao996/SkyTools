package manager.ui.topbar

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import myWindow
import ui.infobar.InfoBarScope

object InfoBarManager {
	fun init() {
		push {
			RightGroup("Window", -1) {
				var isAlwaysOnTop by remember { mutableStateOf(myWindow.isAlwaysOnTop) }
				ActionButton(
					icon = "icons/top.svg",
					iconModifier = Modifier.rotate(if (isAlwaysOnTop) 0F else 45F)
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
