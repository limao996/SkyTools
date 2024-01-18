package core.plugin

import config.UIConfig
import core.Service
import manager.core.ThemeManager
import java.io.File

abstract class PluginService : Service {
	lateinit var config: PluginConfig
	val dataDir = File("assets/plugin/" + config.packageName)

	init {
		dataDir.mkdirs()
	}

	fun setThemeMode(mode: ThemeMode) {
		ThemeManager.current = when (mode) {
			ThemeMode.System -> ThemeManager.Light
			ThemeMode.Light -> ThemeManager.Dark
			ThemeMode.Dark -> ThemeManager.System
		}
		UIConfig.theme = ThemeManager.current
	}

	enum class ThemeMode {
		Light, Dark, System
	}
}