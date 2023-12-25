package ui.topbar

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.ActionButton
import io.kanro.compose.jetbrains.expui.control.Icon
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
	onClick: () -> Unit,
) {
	FixedTooltip(tooltip, message, detail) {
		ActionButton(
			onClick, Modifier.size(40.dp), shape = RectangleShape
		) {
			Icon(icon)
		}
	}
}

