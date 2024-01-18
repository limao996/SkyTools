package core.plugin

import config.UIConfig
import core.Service
import manager.core.ThemeManager
import java.io.File

abstract class PluginService : Service {
	lateinit var config: PluginConfig
	var dataDir = File("assets/plugin/" + config.packageName)

	init {
		dataDir.mkdirs()
	}

	fun setDataDir(dir: File) {
		dataDir = dir
	}

	fun getDataDir() = dataDir

	fun setThemeMode(mode: ThemeMode) {
		ThemeManager.current = when (mode) {
			ThemeMode.System -> ThemeManager.Light
			ThemeMode.Light -> ThemeManager.Dark
			ThemeMode.Dark -> ThemeManager.System
		}
		UIConfig.theme = ThemeManager.current
	}

	fun getThemeMode() = when (ThemeManager.current) {
		ThemeManager.System -> ThemeMode.Light
		ThemeManager.Light -> ThemeMode.Dark
		ThemeManager.Dark -> ThemeMode.System
	}

	enum class ThemeMode {
		Light, Dark, System
	}
}