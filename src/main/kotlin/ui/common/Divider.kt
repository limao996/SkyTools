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
		Modifier.background(
			if (isHide) Color.Transparent
			else if (isHighlight) LocalAreaColors.current.focusColor
			else LocalAreaColors.current.endBorderColor
		).height(if (isHighlight) 3.dp else 1.dp).fillMaxWidth().then(modifier)
	)
}

@Composable
fun VDivider(
	isHighlight: Boolean = false,
	isHide: Boolean = false,
	modifier: Modifier = Modifier,
) {
	Spacer(
		Modifier.background(
			if (isHide) Color.Transparent
			else if (isHighlight) LocalAreaColors.current.focusColor
			else LocalAreaColors.current.endBorderColor
		).width(if (isHighlight) 3.dp else 1.dp).fillMaxHeight().then(modifier)
	)
}

@Composable
fun HSubDivider(
	usePadding: Boolean = true,
	modifier: Modifier = Modifier,
) {
	Spacer(
		Modifier.padding(horizontal = if (usePadding) 8.dp else 0.dp).background(
			if (ThemeManager.current.isDark()) DarkTheme.Grey4
			else LightTheme.Grey9
		).height(1.dp).fillMaxWidth().then(modifier)
	)
}

@Composable
fun VSubDivider(
	usePadding: Boolean = true,
	modifier: Modifier = Modifier,
) {
	Spacer(
		Modifier.padding(vertical = if (usePadding) 8.dp else 0.dp).background(
			if (ThemeManager.current.isDark()) DarkTheme.Grey4
			else LightTheme.Grey9
		).width(1.dp).fillMaxHeight().then(modifier)
	)
}