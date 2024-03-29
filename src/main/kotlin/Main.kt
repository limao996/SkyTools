import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.kanro.compose.jetbrains.expui.theme.DarkTheme
import io.kanro.compose.jetbrains.expui.theme.LightTheme
import io.kanro.compose.jetbrains.expui.window.JBWindow
import manager.core.ResourceManager
import manager.core.ThemeManager
import manager.ui.PopupManager
import manager.ui.sidebar.SideBarManager
import manager.ui.tabpage.TabPageManager
import manager.ui.topbar.InfoBarManager
import manager.ui.topbar.TopBarManager
import ui.Body
import ui.topbar.TopBar
import java.awt.Component
import java.net.URI

const val appTitle = "SkyTools"
const val sdkCode = 0
val githubUri: URI = URI.create("https://github.com/limao996/SkyTools")
val giteeUri: URI = URI.create("https://gitee.com/limao996/SkyTools")

lateinit var myWindow: ComposeWindow
lateinit var component: Component

fun main() {

	ResourceManager.init()
	InfoBarManager.init()
	TopBarManager.init()
	SideBarManager.init()
	TabPageManager.init()

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
			//Popup
			PopupManager.show()
		}
	}
}
