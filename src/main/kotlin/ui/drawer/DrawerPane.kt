package ui.drawer

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.*
import io.kanro.compose.jetbrains.expui.style.LocalAreaColors
import io.kanro.compose.jetbrains.expui.style.LocalMediumTextStyle
import manager.ui.drawer.DrawerScope
import ui.common.FixedTooltip
import ui.common.HDivider
import  ui.drawer.DividerMode.Auto


@LayoutScopeMarker
@Immutable
open class DrawerPaneScope(val upScope: DrawerScope) {
	var showCloseAction by mutableStateOf(false)
	lateinit var scrollState: ScrollState
}

@LayoutScopeMarker
@Immutable
object DrawerPaneToolBarScope

@Composable
fun DrawerScope.DrawerPane(
	modifier: Modifier = Modifier,
	dividerMode: DividerMode = Auto,
	enableScroll: Boolean = true,
	toolbar: @Composable DrawerPaneScope.() -> Unit,
	header: (@Composable DrawerPaneScope.() -> Unit)? = null,
	footer: (@Composable DrawerPaneScope.() -> Unit)? = null,
	content: (@Composable DrawerPaneScope.() -> Unit)? = null,
) {
	val drawerPaneScope = rememberSaveable { DrawerPaneScope(this) }
	drawerPaneScope.scrollState = rememberScrollState()

	Column(modifier.fillMaxSize().onHover {
		drawerPaneScope.showCloseAction = it
	}) {
		//工具栏
		drawerPaneScope.toolbar()

		if (header != null) {
			HDivider(
				isHide = false
			)
			drawerPaneScope.header()
		}
		HDivider(
			isHide = when (dividerMode) {
				Auto -> drawerPaneScope.scrollState.value == 0
				DividerMode.Show -> false
				DividerMode.Hide -> true
			}
		)
		//内容
		Row(Modifier.fillMaxSize().weight(1f).padding(4.dp)) {
			Column(
				(if (enableScroll) Modifier.verticalScroll(drawerPaneScope.scrollState)
				else Modifier).fillMaxSize().weight(1F)
			) { content?.let { drawerPaneScope.it() } }
			if (enableScroll) VerticalScrollbar(
				modifier = Modifier.fillMaxHeight(),
				adapter = rememberScrollbarAdapter(drawerPaneScope.scrollState),
				style = LocalScrollbarStyle.current.copy(
					hoverColor = LocalAreaColors.current.text,
					unhoverColor = LocalAreaColors.current.text.copy(alpha = 0.2F)
				)
			)
		}
		//尾部
		if (footer != null) {
			HDivider(
				isHide = when (dividerMode) {
					Auto -> drawerPaneScope.scrollState.value == drawerPaneScope.scrollState.maxValue
					DividerMode.Show -> false
					DividerMode.Hide -> true
				}
			)
			drawerPaneScope.footer()
		}
	}
}

enum class DividerMode {
	Show, Hide, Auto
}

@Composable
fun DrawerPaneScope.ActionBar(
	modifier: Modifier = Modifier,
	content: @Composable DrawerPaneToolBarScope.() -> Unit,
) {
	Row(
		Modifier.height(35.dp).padding(horizontal = 4.dp).then(modifier),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalAlignment = Alignment.CenterVertically,
	) { DrawerPaneToolBarScope.content() }
}

@Composable
fun DrawerPaneScope.ToolBar(
	modifier: Modifier = Modifier,
	title: String? = null,
	hideCloseAction: Boolean = false,
	actions: @Composable DrawerPaneToolBarScope.() -> Unit = {},
	content: @Composable DrawerPaneToolBarScope.() -> Unit = {},
) {
	val drawerPaneScope = this
	Row(
		Modifier.fillMaxWidth().height(40.dp).padding(horizontal = 12.dp).then(modifier),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(10.dp)
	) {
		if (title != null) Label(title, style = LocalMediumTextStyle.current)
		DrawerPaneToolBarScope.content()
		Row(
			Modifier.fillMaxSize(),
			horizontalArrangement = Arrangement.End,
			verticalAlignment = Alignment.CenterVertically
		) {
			DrawerPaneToolBarScope.actions()
			if (drawerPaneScope.showCloseAction && !hideCloseAction) {
				DrawerPaneToolBarScope.Action(
					"隐藏",
					"icons/hide.svg"
				) {
					drawerPaneScope.upScope.hide()
				}
			}
		}
	}
}

@Composable
fun DrawerPaneToolBarScope.Action(
	label: String, icon: String,
	modifier: Modifier = Modifier, onClick: () -> Unit,
) {
	FixedTooltip(label, modifier = modifier.size(20.dp)) {
		ActionButton(
			onClick, Modifier.fillMaxSize(), shape = RectangleShape,
		) {
			Icon(icon, modifier = Modifier.size(20.dp))
		}
	}
}

@Composable
fun DrawerPaneScope.ToolBarAction(
	label: String, icon: String,
	modifier: Modifier = Modifier, onClick: () -> Unit,
) {
	FixedTooltip(label, modifier = modifier.size(20.dp)) {
		ActionButton(
			onClick, Modifier.fillMaxSize(), shape = RectangleShape,
		) {
			Icon(icon, modifier = Modifier.size(20.dp))
		}
	}
}