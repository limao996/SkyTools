package manager.ui.topbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import config.UIConfig
import giteeUri
import githubUri
import manager.core.ThemeManager
import manager.ui.PopupManager
import ui.common.popup.MenuItem
import ui.common.popup.Title
import ui.topbar.Action
import ui.topbar.TopBarScope
import java.awt.Desktop

object TopBarManager {
	fun init() {
		push {
			ActionGroup("Github & Theme", -1) {
				//Github
				Action(
					"开源仓库", "limao996", icon = "icons/github.svg"
				) {
					val dp = Desktop.getDesktop()
					if (dp.isSupported(Desktop.Action.BROWSE)) {
						PopupManager.open {
							Title("开源仓库")
							MenuItem(
								"icons/gitee.svg", "Gitee", "$giteeUri"
							) {
								dp.browse(giteeUri)
							}
							MenuItem(
								"icons/github.svg", "Github", "$githubUri"
							) {
								dp.browse(githubUri)
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
			}
		}
	}

	val header = mutableStateListOf<Group>()
	val actions = mutableStateListOf<Group>()

	fun reset() {
		header.clear()
		actions.clear()
		init()
	}

	fun sort() {
		header.sortBy { group -> group.priority }
		actions.sortByDescending { group -> group.priority }
	}

	fun push(fn: TopBarManager.() -> Unit) {
		TopBarManager.fn()
	}

	data class Group(
		val tag: String,
		val priority: Int,
		val content: @Composable() TopBarScope.() -> Unit,
	)

	fun HeaderGroup(
		tag: String,
		priority: Int,
		content: @Composable() TopBarScope.() -> Unit,
	) {
		header.add(
			Group(
				tag, priority, content
			)
		)
	}

	fun ActionGroup(
		tag: String,
		priority: Int,
		content: @Composable() TopBarScope.() -> Unit,
	) {
		actions.add(
			Group(
				tag, priority, content
			)
		)
	}
}
