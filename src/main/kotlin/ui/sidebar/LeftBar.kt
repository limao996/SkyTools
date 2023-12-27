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
import ui.common.FixedTooltip
import ui.common.FixedTooltipPlacement
import ui.common.HSubDivider
import ui.common.JBIcon

@Composable
fun LeftBar() {
	Column(
		Modifier.fillMaxHeight().width(40.dp).padding(vertical = 4.dp),
		verticalArrangement = Arrangement.spacedBy(10.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		var selected by remember { mutableStateOf(0) }
		FixedTooltip(
			"Github", delayMillis = 0, placement = FixedTooltipPlacement.Right
		) {
			ToolBarActionButton(
				selected == 0,
				{ },
				modifier = Modifier.size(30.dp),
			) {
				Box(contentAlignment = Alignment.Center) {
					JBIcon(
						"icons/github.svg"
					)
				}
			}
		}
		HSubDivider()
		ToolBarActionButton(
			selected == 1, { selected = 1 }, modifier = Modifier.size(30.dp)
		) {
			Box(contentAlignment = Alignment.Center) {
				JBIcon(
					"icons/github.svg",
					markerColor = LocalErrorAreaColors.current.text,
				)
			}
		}
	}
}