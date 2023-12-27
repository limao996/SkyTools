package manager.ui.topbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.unit.dp
import config.UIConfig
import giteeUri
import githubUri
import io.kanro.compose.jetbrains.expui.control.Label
import manager.core.ThemeManager
import manager.ui.PopupManager
import ui.common.JBIcon
import ui.common.MenuItem
import ui.common.Message
import ui.common.Title
import ui.topbar.Action
import ui.topbar.TopBarScope
import java.awt.Desktop

object TopBarManager {
	fun init() {
		actions.add(Group("Github & Theme", -1) {
			//Github
			Action(
				"开源仓库", "limao996", icon = "icons/github.svg"
			) {
				val dp = Desktop.getDesktop()
				if (dp.isSupported(Desktop.Action.BROWSE)) {
					PopupManager.open {
						Title("开源仓库")
						MenuItem({
							dp.browse(giteeUri)
						}, message = {
							Message("$giteeUri")
						}) {
							JBIcon(
								"icons/gitee.svg", 16.dp
							)
							Label("Gitee")
						}
						MenuItem({
							dp.browse(githubUri)
						}, message = {
							Message("$githubUri")
						}) {
							JBIcon(
								"icons/github.svg", 16.dp
							)
							Label("Github")
						}
					}
				}
			}
			//Theme
			Action(
				when (ThemeManager.current) {
					ThemeManager.System -> "跟随系统"
					ThemeManager.Light -> "日间模式"
					ThemeManager.Dark -> "夜间模式"
				}, icon = when (ThemeManager.current) {
					ThemeManager.System -> "icons/systemTheme.svg"
					ThemeManager.Light -> "icons/lightTheme.svg"
					ThemeManager.Dark -> "icons/darkTheme.svg"
				}
			) {
				ThemeManager.current = when (ThemeManager.current) {
					ThemeManager.System -> ThemeManager.Light
					ThemeManager.Light -> ThemeManager.Dark
					ThemeManager.Dark -> ThemeManager.System
				}
				UIConfig.theme = ThemeManager.current
			}
		})
	}

	val header = mutableStateListOf<Group>()
	val actions = mutableStateListOf<Group>()

	fun clear() {
		header.clear()
		actions.clear()
	}

	fun sort() {
		header.sortBy { group -> group.priority }
		actions.sortByDescending { group -> group.priority }
	}

	data class Group(
		val tag: String,
		val priority: Int,
		val content: @Composable() TopBarScope.() -> Unit,
	)
}
