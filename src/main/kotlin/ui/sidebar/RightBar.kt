package ui.sidebar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.Icon
import io.kanro.compose.jetbrains.expui.control.ToolBarActionButton
import io.kanro.compose.jetbrains.expui.style.LocalErrorAreaColors

@Composable
fun RightBar() {
	Column(
		Modifier.fillMaxHeight().width(40.dp).padding(vertical = 4.dp),
		verticalArrangement = Arrangement.spacedBy(10.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		var selected by remember { mutableStateOf(0) }
		ToolBarActionButton(
			selected == 0, { selected = 0 }, modifier = Modifier.size(30.dp)
		) {
			Icon("icons/github.svg")
		}
		ToolBarActionButton(
			selected == 1, { selected = 1 }, modifier = Modifier.size(30.dp)
		) {
			Icon("icons/github.svg", markerColor = LocalErrorAreaColors.current.text)
		}
	}
}