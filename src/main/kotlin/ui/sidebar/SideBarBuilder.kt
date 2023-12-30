package ui.sidebar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.ToolBarActionButton
import io.kanro.compose.jetbrains.expui.style.LocalErrorAreaColors
import manager.ui.sidebar.SideBarManager
import manager.ui.sidebar.SideBarManager.SideBarAction
import ui.common.FixedTooltip
import ui.common.FixedTooltipPlacement
import ui.common.JBIcon
import ui.sidebar.SideBarBuilder.Placement


class SideBarBuilder(
	val placement: Placement,
) {
	var selected by mutableStateOf<SideBarAction?>(null)

	val list = when (placement) {
		Placement.LeftFirst -> SideBarManager.leftFirst
		Placement.LeftLast -> SideBarManager.leftLast
		Placement.BottomFirst -> SideBarManager.bottomFirst
		Placement.RightFirst -> SideBarManager.rightFirst
		Placement.RightLast -> SideBarManager.rightLast
		Placement.BottomLast -> SideBarManager.bottomLast
	}

	enum class Placement {
		LeftFirst, LeftLast, BottomFirst, RightFirst, RightLast, BottomLast
	}

	fun Action(
		tag: String,
		priority: Int,
		icon: String,
		tooltip: String,
		message: String? = null,
		detail: String? = null,
		marker: Boolean = false,
		onClick: SideBarScope.() -> Unit,
	) {
		lateinit var action: SideBarAction
		action = SideBarAction(
			tag, priority
		) {
			ActionButton(
				icon, action == selected, tooltip, placement, message, detail, marker
			) { SideBarScope(this, action).onClick() }
		}
		list.add(action)
	}
}


@Composable
private fun ActionButton(
	icon: String,
	selected: Boolean,
	tooltip: String,
	placement: Placement,
	message: String? = null,
	detail: String? = null,
	marker: Boolean = false,
	onClick: () -> Unit,
) {
	FixedTooltip(
		tooltip,
		message,
		detail,
		delayMillis = 0,
		placement = if (placement.ordinal < 3) FixedTooltipPlacement.Right else FixedTooltipPlacement.Left
	) {
		ToolBarActionButton(
			selected,
			onClick,
			modifier = Modifier.size(30.dp),
		) {
			Box(contentAlignment = Alignment.Center) {
				JBIcon(
					icon,
					markerColor = if (marker) {
						LocalErrorAreaColors.current.text
					} else Color.Unspecified,
				)
			}
		}
	}
}


