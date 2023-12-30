package ui.sidebar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import manager.ui.sidebar.SideBarManager
import ui.common.HSubDivider

@Composable
fun LeftBar() {
	with(SideBarManager) {
		sortLeftBar()
		if (leftFirst.isNotEmpty() || leftLast.isNotEmpty() || bottomFirst.isNotEmpty()) Column(
			Modifier.fillMaxHeight().width(40.dp).padding(vertical = 4.dp),
			verticalArrangement = Arrangement.spacedBy(10.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			for (action in leftFirst) {
				action.content(LeftFirstBuilder)
			}
			if (leftFirst.isNotEmpty() && leftLast.isNotEmpty()) HSubDivider()
			for (action in leftLast) {
				action.content(LeftLastBuilder)
			}
			Spacer(Modifier.weight(1F))
			for (action in bottomFirst) {
				action.content(BottomFirstBuilder)
			}
		}
	}
}