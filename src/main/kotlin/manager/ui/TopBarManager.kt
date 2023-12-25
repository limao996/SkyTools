package manager.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import config.UIConfig
import githubUri
import manager.core.ThemeManager
import ui.topbar.Action
import ui.topbar.TopBarScope
import java.awt.Desktop

object TopBarManager {
	fun init() {
		right.add(View("Github") {
			Action(
				"开源仓库", "limao996", "$githubUri", icon = "icons/github@20x20.svg"
			) {
				val dp = Desktop.getDesktop()
				if (dp.isSupported(Desktop.Action.BROWSE)) {
					dp.browse(githubUri)
				}
			}
		})
		right.add(View("Theme") {
			Action(
				if (ThemeManager.current.isDark()) "夜间主题" else "日间主题",
				icon = if (ThemeManager.current.isDark()) "icons/darkTheme@20x20.svg"
				else "icons/lightTheme@20x20.svg"
			) {
				ThemeManager.current =
					if (ThemeManager.current.isDark()) ThemeManager.Light else ThemeManager.Dark
				UIConfig.theme = ThemeManager.current
			}
		})
	}

	val left = mutableStateListOf<View>()
	val right = mutableStateListOf<View>()

	data class View(
		val label: String,
		val content: @Composable() TopBarScope.() -> Unit,
	)
}
