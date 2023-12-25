import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import io.kanro.compose.jetbrains.expui.theme.DarkTheme
import io.kanro.compose.jetbrains.expui.theme.LightTheme
import io.kanro.compose.jetbrains.expui.window.JBWindow
import manager.core.ThemeManager
import manager.ui.TopBarManager
import ui.Body
import ui.topbar.TopBar
import java.awt.Component
import java.io.File
import java.net.URI

const val appTitle = "SkyTools"
val githubUri: URI = URI.create("https://github.com/limao996")

lateinit var myWindow: ComposeWindow
lateinit var component: Component
fun main() {
	File("assets").apply {
		if (!exists()) {
			mkdir()
		}
	}
	TopBarManager.init()

	application {
		JBWindow(::exitApplication,
			title = appTitle,
			showTitle = true,
			theme = if (ThemeManager.current.isDark()) DarkTheme else LightTheme,
			state = rememberWindowState(
				placement = WindowPlacement.Maximized,
				size = DpSize((900 * 1.5).dp, (600 * 1.5).dp),
			),
			mainToolBar = { TopBar() }) {
			myWindow = window
			component = myWindow.getComponent(0)
			//主窗格
			Body()
		}
	}

}
