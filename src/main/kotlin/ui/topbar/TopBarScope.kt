package ui.topbar

import androidx.compose.foundation.Indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.ActionButton
import io.kanro.compose.jetbrains.expui.control.ActionButtonColors
import io.kanro.compose.jetbrains.expui.control.HoverOrPressedIndication
import io.kanro.compose.jetbrains.expui.control.LocalActionButtonColors
import ui.common.FixedTooltip
import ui.common.JBIcon

@LayoutScopeMarker
@Immutable
object TopBarScope

@Composable
fun TopBarScope.Action(
	tooltip: String,
	message: String? = null,
	detail: String? = null,
	icon: String,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	shape: Shape = RectangleShape,
	indication: Indication? = HoverOrPressedIndication(shape),
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	colors: ActionButtonColors = LocalActionButtonColors.current,
	markerColor: Color = Color.Unspecified,
	onClick: () -> Unit,
) {
	FixedTooltip(tooltip, message, detail) {
		ActionButton(
			onClick, Modifier.size(40.dp).then(modifier),
			enabled = enabled,
			shape = shape,
			indication = indication,
			interactionSource = interactionSource,
			colors = colors,
		) {
			Box(contentAlignment = Alignment.Center) {
				JBIcon(
					icon, markerColor = markerColor
				)
			}
		}
	}
}

