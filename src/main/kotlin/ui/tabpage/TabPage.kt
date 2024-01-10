package ui.tabpage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.*
import io.kanro.compose.jetbrains.expui.style.LocalAreaColors
import io.kanro.compose.jetbrains.expui.style.areaBackground
import manager.ui.tabpage.TabPageManager
import ui.common.HSubDivider
import ui.common.JBIcon

@Composable
fun TabPage() {
	Row(
		Modifier.height(40.dp).selectableGroup().horizontalScroll(
			rememberScrollState()
		).fillMaxWidth()
	) {
		for (tab in TabPageManager.list) {
			Tab(
				icon = tab.icon,
				label = tab.label,
				showCloseButton = tab.showCloseButton,
				selected = TabPageManager.selected == tab,
				onSelected = { tab.onSelected(tab) },
				onClosed = { tab.onClosed(tab) },
				modifier = Modifier.height(40.dp)
			)
		}
	}
	HSubDivider(false)
	Box(Modifier.fillMaxSize().padding(8.dp)) {
		TabPageManager.selected?.page?.invoke()
	}
}

@Composable
private fun Tab(
	icon: String,
	label: String,
	selected: Boolean,
	onSelected: () -> Unit,
	modifier: Modifier = Modifier,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	colors: TabColors = LocalCloseableTabColors.current,
	showCloseButton: Boolean = true,
	onClosed: () -> Unit,
) {
	colors.provideArea(selected) {
		val currentColors = LocalAreaColors.current
		var hover by remember { mutableStateOf(false) }
		Box(modifier.areaBackground().drawWithCache {
			onDrawWithContent {
				drawContent()
				if (selected) {
					val strokeWidth = 3.dp.toPx()
					val start = Offset(strokeWidth / 2f, size.height - (strokeWidth / 2f))
					val end = start.copy(x = size.width - strokeWidth / 2f)
					drawLine(
						currentColors.focusColor, start, end, strokeWidth, cap = StrokeCap.Round
					)
				}
			}
		}.focusProperties {
			canFocus = false
		}.selectable(
			selected = selected,
			enabled = true,
			onClick = onSelected,
			role = Role.Tab,
			interactionSource = interactionSource,
			indication = HoverOrPressedIndication(RectangleShape),
		).onHover {
			hover = it
		}.padding(horizontal = 12.dp)
		) {
			Row(
				modifier = Modifier
					.align(Alignment.Center)
					.graphicsLayer(alpha = if (hover || selected) 1f else 0.7f),
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				JBIcon(icon, 16.dp)
				Label(label)
				CloseButton((hover || selected) && showCloseButton, onClosed)
			}
		}
	}
}

@Composable
private fun CloseButton(
	shown: Boolean,
	onClosed: () -> Unit,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
	var hover by remember { mutableStateOf(false) }
	Box(modifier = Modifier.size(16.dp).areaBackground().focusProperties {
		canFocus = false
	}.clickable(
		enabled = shown,
		onClick = onClosed,
		role = Role.Button,
		interactionSource = interactionSource,
		indication = null,
	).onHover {
		hover = it
	}) {
		if (shown) {
			Icon(
				if (hover) "icons/closeSmallHovered.svg" else "icons/closeSmall.svg",
				contentDescription = "Close"
			)
		}
	}
}
