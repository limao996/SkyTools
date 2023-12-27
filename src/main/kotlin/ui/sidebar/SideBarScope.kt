package ui.sidebar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.ToolBarActionButton
import io.kanro.compose.jetbrains.expui.style.LocalErrorAreaColors
import ui.common.FixedTooltip
import ui.common.FixedTooltipPlacement
import ui.common.JBIcon


@LayoutScopeMarker
@Immutable
class SideBarScope(
	val placement: SideBarPlacement,
)

enum class SideBarPlacement() {
	LeftFirst, LeftLast, BottomFirst, RightFirst, RightLast, BottomLast
}

@Composable
fun SideBarScope.Action(
	tooltip: String,
	message: String? = null,
	detail: String? = null,
	icon: String,
	marker: Boolean = false,
	onClick: SideBarScope.() -> Unit,
) {
	FixedTooltip(
		tooltip,
		message,
		detail,
		delayMillis = 0,
		placement = if (placement.ordinal < 3) FixedTooltipPlacement.Right else FixedTooltipPlacement.Left
	) {
		ToolBarActionButton(
			false,
			{ this.onClick() },
			modifier = Modifier.size(30.dp),
		) {
			Box(contentAlignment = Alignment.Center) {
				JBIcon(
					icon,
					markerColor = if (marker) LocalErrorAreaColors.current.text else Color.Unspecified,
				)
			}
		}
	}
}