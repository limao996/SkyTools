package manager.core

import androidx.compose.runtime.*
import config.UIConfig
import org.jetbrains.skiko.SystemTheme
import org.jetbrains.skiko.currentSystemTheme

enum class ThemeManager {
	Light, Dark, System;

	fun isDark() = (if (this == System) fromSystemTheme(currentSystemTheme) else this) == Dark

	companion object {

		fun fromSystemTheme(systemTheme: SystemTheme) =
			if (systemTheme == SystemTheme.LIGHT) Light else Dark

		var current: ThemeManager by mutableStateOf(UIConfig.theme)
	}
}
