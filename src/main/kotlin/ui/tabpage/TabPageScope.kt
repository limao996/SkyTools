package ui.tabpage

import androidx.compose.runtime.Composable
import manager.ui.tabpage.TabPageManager
import manager.ui.tabpage.TabPageManager.Tab

object TabPageScope {
	fun Tab(
		tag: String,
		icon: String = "icons/meetNewUi.svg",
		label: String,
		showCloseButton: Boolean = true,
		onSelected: (Tab) -> Boolean = { false },
		onClosed: (Tab) -> Boolean = { false },
		page: @Composable () -> Unit,
	): Tab {
		return with(TabPageManager) {
			val tab: Tab = TabPageManager.Tab(
				tag = tag,
				icon = icon,
				label = label,
				showCloseButton = showCloseButton,
				onSelected = {
					if (!onSelected(it)) {
						selected = it
						onSelected(it)
					}
				},
				onClosed = {
					if (!onClosed(it)) {
						if (selected == it) selected = list[list.lastIndexOf(it) - 1]
						list.remove(it)
					}
				},
				page = page
			)
			list.add(
				tab
			)
			return tab
		}
	}
}

