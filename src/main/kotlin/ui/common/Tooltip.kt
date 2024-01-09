package ui.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.Label
import io.kanro.compose.jetbrains.expui.control.LocalToolTipColors
import io.kanro.compose.jetbrains.expui.control.ToolTipColors
import io.kanro.compose.jetbrains.expui.style.LocalAreaColors
import io.kanro.compose.jetbrains.expui.style.areaBackground
import io.kanro.compose.jetbrains.expui.theme.DarkTheme
import io.kanro.compose.jetbrains.expui.theme.LightTheme
import manager.core.ThemeManager

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun FixedTooltip(
	tooltip: @Composable () -> Unit,
	delayMillis: Int = 500,
	modifier: Modifier = Modifier,
	colors: ToolTipColors = LocalToolTipColors.current,
	placement: FixedTooltipPlacement = FixedTooltipPlacement.Bottom,
	enabled: Boolean = true,
	content: @Composable () -> Unit,
) {
	if (!enabled) content() else TooltipArea(
		{
			colors.provideArea {
				Box(
					modifier = Modifier.shadow(
						8.dp, RoundedCornerShape(4.dp)
					).areaBackground().border(
						1.dp, LocalAreaColors.current.startBorderColor, RoundedCornerShape(4.dp)
					), contentAlignment = Alignment.Center
				) {
					Box(
						modifier = Modifier.padding(
							horizontal = 12.dp, vertical = 8.dp
						),
					) {
						tooltip()
					}
				}
			}
		}, modifier, delayMillis, TooltipPlacement.ComponentRect(
			anchor = placement.alignment, alignment = placement.alignment, offset = placement.offset
		), content
	)
}

@Composable
fun FixedTooltip(
	tooltip: String? = null,
	message: String? = null,
	detail: String? = null,
	delayMillis: Int = 500,
	modifier: Modifier = Modifier,
	colors: ToolTipColors = LocalToolTipColors.current,
	placement: FixedTooltipPlacement = FixedTooltipPlacement.Bottom,
	enabled: Boolean = true,
	content: @Composable () -> Unit,
) {
	FixedTooltip(
		{
			Column(
				verticalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(8.dp)
				) {
					tooltip?.let { Label(it) }
					if (message != null) Label(
						message, color = if (ThemeManager.current.isDark()) DarkTheme.Grey8
						else LightTheme.Grey8
					)
				}
				if (detail != null) Label(
					detail, color = if (ThemeManager.current.isDark()) DarkTheme.Grey8
					else LightTheme.Grey8
				)
			}
		}, delayMillis, modifier, colors, placement, enabled, content
	)
}

enum class FixedTooltipPlacement(val alignment: Alignment, val offset: DpOffset) {
	Left(Alignment.CenterStart, DpOffset((-8).dp, 0.dp)),
	Top(Alignment.TopCenter, DpOffset(0.dp, (-8).dp)),
	Right(Alignment.CenterEnd, DpOffset(8.dp, 0.dp)),
	Bottom(Alignment.BottomCenter, DpOffset(0.dp, 8.dp)),
	TopLeft(Alignment.TopStart, DpOffset((-8).dp, (-8).dp)),
	TopRight(Alignment.TopEnd, DpOffset(8.dp, (-8).dp)),
	BottomLeft(Alignment.BottomStart, DpOffset((-8).dp, 8.dp)),
	BottomRight(Alignment.BottomEnd, DpOffset(8.dp, 8.dp)),
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun FollowTooltip(
	tooltip: @Composable () -> Unit,
	delayMillis: Int = 500,
	modifier: Modifier = Modifier,
	colors: ToolTipColors = LocalToolTipColors.current,
	enabled: Boolean = true,
	content: @Composable () -> Unit,
) {
	if (!enabled) content() else TooltipArea(
		{
			colors.provideArea {
				Box(
					modifier = Modifier.shadow(
						8.dp, RoundedCornerShape(4.dp)
					).areaBackground().border(
						1.dp, LocalAreaColors.current.startBorderColor, RoundedCornerShape(4.dp)
					), contentAlignment = Alignment.Center
				) {
					Box(
						modifier = Modifier.padding(
							horizontal = 12.dp, vertical = 8.dp
						),
					) {
						tooltip()
					}
				}
			}
		},
		modifier,
		delayMillis,
		TooltipPlacement.CursorPoint(offset = DpOffset(0.dp, 32.dp)),
		content
	)
}

@Composable
fun FollowTooltip(
	tooltip: String,
	message: String? = null,
	detail: String? = null,
	delayMillis: Int = 500,
	modifier: Modifier = Modifier,
	colors: ToolTipColors = LocalToolTipColors.current,
	enabled: Boolean = true,
	content: @Composable () -> Unit,
) {
	FollowTooltip(
		{
			Column(
				verticalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(8.dp)
				) {
					Label(tooltip)
					if (message != null) Label(
						message, color = if (ThemeManager.current.isDark()) DarkTheme.Grey8
						else LightTheme.Grey8
					)
				}
				if (detail != null) Label(
					detail, color = if (ThemeManager.current.isDark()) DarkTheme.Grey8
					else LightTheme.Grey8
				)
			}
		}, delayMillis, modifier, colors, enabled, content
	)
}