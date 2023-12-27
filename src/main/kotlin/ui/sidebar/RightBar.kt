package ui.sidebar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.ToolBarActionButton
import io.kanro.compose.jetbrains.expui.style.LocalErrorAreaColors
import ui.common.JBIcon

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
			Box(contentAlignment = Alignment.Center) {
				JBIcon(
					"icons/github.svg"
				)
			}
		}
		ToolBarActionButton(
			selected == 1, { selected = 1 }, modifier = Modifier.size(30.dp)
		) {
			Box(contentAlignment = Alignment.Center) {
				JBIcon(
					"icons/github.svg", markerColor = LocalErrorAreaColors.current.text
				)
			}
		}
	}
}