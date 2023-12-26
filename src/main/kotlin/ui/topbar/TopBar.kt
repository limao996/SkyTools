package ui.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.kanro.compose.jetbrains.expui.window.MainToolBarScope
import manager.ui.topbar.TopBarManager

@Composable
fun MainToolBarScope.TopBar() {
	TopBarManager.sort()
	//头部
	Row(
		Modifier.mainToolBarItem(
			Alignment.Start, false
		)
	) {
		for (v in TopBarManager.header) v.content(TopBarScope)
	}
	//拖动区
	Row(
		Modifier.mainToolBarItem(
			Alignment.End, true
		)
	) { }
	//动作栏
	Row(
		Modifier.mainToolBarItem(
			Alignment.End, false
		)
	) {
		for (v in TopBarManager.actions) v.content(TopBarScope)
	}
}

