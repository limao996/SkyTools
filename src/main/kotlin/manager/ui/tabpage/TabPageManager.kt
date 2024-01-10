package manager.ui.tabpage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.kanro.compose.jetbrains.expui.control.Label
import io.kanro.compose.jetbrains.expui.control.OutlineButton
import ui.tabpage.TabPageScope

object TabPageManager {
	fun init() {
		push {
			selected = Tab(
				tag = "Home",
				icon = "icons/home.svg",
				label = "首页",
				showCloseButton = false,
			) {
				OutlineButton({}) { Label("1") }
			}

			Tab(
				tag = "Music Sheet",
				label = "乐谱生成",
			) {
				OutlineButton({}) { Label("2") }
			}
		}
	}

	val list = mutableStateListOf<Tab>()
	var selected by mutableStateOf<Tab?>(null)

	data class Tab(
		val tag: String,
		val icon: String = "icons/meetNewUi.svg",
		val label: String,
		val showCloseButton: Boolean = true,
		val onSelected: (Tab) -> Unit = {},
		val onClosed: (Tab) -> Unit = {},
		val page: @Composable () -> Unit,
	)

	fun push(content: TabPageScope.() -> Unit) {
		TabPageScope.content()
	}

	fun find(tag: String): Tab {
		lateinit var tab: Tab
		list.forEach {
			if (it.tag == tag) {
				tab = it
				return@forEach
			}
		}
		return tab
	}

	fun remove(tab: Tab) {
		list.remove(tab)
	}

	fun remove(tag: String) = remove(find(tag))
}