package ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.style.LocalAreaColors
import io.kanro.compose.jetbrains.expui.theme.DarkTheme
import io.kanro.compose.jetbrains.expui.theme.LightTheme
import manager.core.ThemeManager

@Composable
fun HDivider(
	isHighlight: Boolean = false,
	isHide: Boolean = false,
	modifier: Modifier = Modifier,
) {
	Spacer(
		modifier.background(
			if (isHide) Color.Companion.Transparent
			else if (isHighlight) LocalAreaColors.current.focusColor
			else LocalAreaColors.current.endBorderColor
		).height(if (isHighlight) 3.dp else 1.dp).fillMaxWidth()
	)
}

@Composable
fun VDivider(
	isHighlight: Boolean = false,
	isHide: Boolean = false,
	modifier: Modifier = Modifier,
) {
	Spacer(
		modifier.background(
			if (isHide) Color.Companion.Transparent
			else if (isHighlight) LocalAreaColors.current.focusColor
			else LocalAreaColors.current.endBorderColor
		).width(if (isHighlight) 3.dp else 1.dp).fillMaxHeight()
	)
}

@Composable
fun HSubDivider(usePadding: Boolean = true) {
	Spacer(
		Modifier
			.padding(horizontal = if (usePadding) 8.dp else 0.dp)
			.background(
				if (ThemeManager.current.isDark()) DarkTheme.Grey4
				else LightTheme.Grey9
			)
			.height(1.dp)
			.fillMaxWidth()
	)
}

@Composable
fun VSubDivider(usePadding: Boolean = true) {
	Spacer(
		Modifier.padding(vertical = if (usePadding) 8.dp else 0.dp).background(
			if (ThemeManager.current.isDark()) DarkTheme.Grey4
			else LightTheme.Grey9
		).width(1.dp).fillMaxHeight()
	)
}