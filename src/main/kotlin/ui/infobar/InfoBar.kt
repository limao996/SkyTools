package ui.infobar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import manager.ui.topbar.InfoBarManager

@Composable
fun InfoBar() {
	InfoBarManager.sort()
	Box(
		Modifier.fillMaxWidth().height(30.dp).padding(horizontal = 12.dp),
		contentAlignment = Alignment.Center
	) {
		for (v in InfoBarManager.center) v.content(InfoBarScope)
		Row {
			for (v in InfoBarManager.left) v.content(InfoBarScope)
			Spacer(Modifier.weight(1F))
			for (v in InfoBarManager.right) v.content(InfoBarScope)
		}
	}
}

