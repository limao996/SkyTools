package ui.infobar

import androidx.compose.foundation.Indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.ActionButtonColors
import io.kanro.compose.jetbrains.expui.control.HoverOrPressedIndication
import io.kanro.compose.jetbrains.expui.control.Label
import io.kanro.compose.jetbrains.expui.control.LocalActionButtonColors
import io.kanro.compose.jetbrains.expui.control.onHover
import ui.common.FixedTooltip
import ui.common.FixedTooltipPlacement.Top
import ui.common.JBIcon

@LayoutScopeMarker
@Immutable
object InfoBarScope {
	@Composable
	fun ActionButton(
		label: String? = null,
		icon: String? = null,
		tooltip: String? = null,
		message: String? = null,
		detail: String? = null,
		modifier: Modifier = Modifier,
		iconModifier: Modifier = Modifier,
		labelModifier: Modifier = Modifier,
		enabled: Boolean = true,
		iconSize: Dp = 16.dp,
		shape: Shape = RoundedCornerShape(0.dp),
		indication: Indication? = HoverOrPressedIndication(shape),
		interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
		colors: ActionButtonColors = LocalActionButtonColors.current,
		onClick: () -> Unit,
	) {
		FixedTooltip(
			tooltip,
			message,
			detail,
			placement = Top,
			enabled = tooltip != null,
		) {
			var isHover by remember { mutableStateOf(false) }
			io.kanro.compose.jetbrains.expui.control.ActionButton(
				onClick = onClick,
				modifier = Modifier.fillMaxHeight().then(modifier).onHover { isHover = it },
				enabled = enabled,
				shape = shape,
				indication = indication,
				interactionSource = interactionSource,
				colors = colors,
			) {
				Row(
					Modifier.padding(horizontal = 8.dp),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(6.dp)
				) {
					icon?.let {
						JBIcon(
							it,
							iconSize,
							modifier = Modifier.alpha(if (isHover) 1F else 0.6F).then(iconModifier)
						)
					}
					label?.let {
						Label(
							it, Modifier.alpha(if (isHover) 1F else 0.6F).then(labelModifier)
						)
					}
				}
			}
		}
	}
}