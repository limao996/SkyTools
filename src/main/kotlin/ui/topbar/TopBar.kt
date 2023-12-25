package ui.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.kanro.compose.jetbrains.expui.window.MainToolBarScope
import manager.ui.TopBarManager

@Composable
fun MainToolBarScope.TopBar() {
	//左侧动作条
	Row(
		Modifier.mainToolBarItem(
			Alignment.Start, false
		)
	) {
		for (v in TopBarManager.left) v.content(TopBarScope)
	}
	// 拖动条
	Row(
		Modifier.mainToolBarItem(
			Alignment.End, true
		)
	) { }
	//右侧动作条
	Row(
		Modifier.mainToolBarItem(
			Alignment.End, false
		)
	) {
		for (v in TopBarManager.right) v.content(TopBarScope)
	}
}

