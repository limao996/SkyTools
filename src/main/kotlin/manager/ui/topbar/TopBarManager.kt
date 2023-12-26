package manager.ui.topbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import config.UIConfig
import giteeUri
import githubUri
import io.kanro.compose.jetbrains.expui.control.Icon
import io.kanro.compose.jetbrains.expui.control.Label
import manager.core.ThemeManager
import manager.ui.PopupManager
import ui.common.MenuItem
import ui.common.Title
import ui.topbar.Action
import ui.topbar.TopBarScope
import java.awt.Desktop

object TopBarManager {
	fun init() {
		actions.add(Group("Github & Theme", -1) {
			//Github
			Action(
				"开源仓库",
				"limao996",
				"Gitee: $giteeUri\nGithub: $githubUri",
				icon = "icons/github@20x20.svg"
			) {
				val dp = Desktop.getDesktop()
				if (dp.isSupported(Desktop.Action.BROWSE)) {
					PopupManager.open {
						Title("开源仓库")
						MenuItem({
							dp.browse(giteeUri)
						}) {
							Icon("icons/gitee.svg")
							Label("Gitee")
						}
						MenuItem({
							dp.browse(githubUri)
						}) {
							Icon("icons/github.svg")
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
					ThemeManager.System -> "icons/systemTheme@20x20.svg"
					ThemeManager.Light -> "icons/lightTheme@20x20.svg"
					ThemeManager.Dark -> "icons/darkTheme@20x20.svg"
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
