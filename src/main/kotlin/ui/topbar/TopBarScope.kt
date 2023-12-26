package ui.topbar

import androidx.compose.foundation.Indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.*
import ui.common.FixedTooltip

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
			Icon(icon, markerColor = markerColor)
		}
	}
}

