package ui.sidebar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import manager.ui.sidebar.SideBarManager
import ui.common.HSubDivider

@Composable
fun RightBar() {
	with(SideBarManager) {
		sortRightBar()
		if (rightFirst.isNotEmpty() || rightLast.isNotEmpty() || bottomLast.isNotEmpty()) Column(
			Modifier.fillMaxHeight().width(40.dp).padding(vertical = 4.dp),
			verticalArrangement = Arrangement.spacedBy(10.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			for (action in rightFirst) {
				action.content(RightFirstBuilder)
			}
			if (rightFirst.isNotEmpty() && rightLast.isNotEmpty()) HSubDivider()
			for (action in rightLast) {
				action.content(RightLastBuilder)
			}
			Spacer(Modifier.weight(1F))
			for (action in bottomLast) {
				action.content(BottomLastBuilder)
			}
		}
	}
}