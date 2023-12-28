package ui.drawer

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import config.UIConfig
import io.kanro.compose.jetbrains.expui.control.ActionButton
import io.kanro.compose.jetbrains.expui.control.Label
import io.kanro.compose.jetbrains.expui.control.onHover
import io.kanro.compose.jetbrains.expui.style.LocalAreaColors
import io.kanro.compose.jetbrains.expui.style.LocalMediumTextStyle
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import manager.ui.PopupManager
import manager.ui.drawer.BottomDrawerManager
import manager.ui.drawer.LeftDrawerManager
import manager.ui.drawer.RightDrawerManager
import ui.common.*
import ui.drawer.DividerMode.Auto


@LayoutScopeMarker
@Immutable
open class DrawerPaneScope(val upScope: DrawerContentScope) {
	var lockHover = false
	var showAction by mutableStateOf(false)
	lateinit var scrollState: ScrollState
}

@LayoutScopeMarker
@Immutable
object DrawerPaneToolBarScope

@Composable
fun DrawerContentScope.DrawerPane(
	modifier: Modifier = Modifier,
	dividerMode: DividerMode = Auto,
	enableScroll: Boolean = true,
	toolbar: (@Composable DrawerPaneScope.() -> Unit)? = null,
	header: (@Composable DrawerPaneScope.() -> Unit)? = null,
	footer: (@Composable DrawerPaneScope.() -> Unit)? = null,
	content: (@Composable DrawerPaneScope.() -> Unit)? = null,
) {
	val drawerPaneScope = rememberSaveable { DrawerPaneScope(this) }
	drawerPaneScope.scrollState = rememberScrollState()

	Column(modifier.fillMaxSize().onHover {
		if (!drawerPaneScope.lockHover) drawerPaneScope.showAction = it
	}) {
		//工具栏
		toolbar?.let { drawerPaneScope.it() }

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

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun DrawerPaneScope.ToolBar(
	title: String? = null,
	hideCloseAction: Boolean = false,
	modifier: Modifier = Modifier,
	items: (@Composable PopupScope.() -> Unit)? = null,
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
		Spacer(Modifier.weight(1F))
		Row(
			horizontalArrangement = Arrangement.spacedBy(4.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			if (drawerPaneScope.showAction && !hideCloseAction) {
				DrawerPaneToolBarScope.actions()
				DrawerPaneToolBarScope.Action(
					"更多", "icons/more.svg"
				) {
					drawerPaneScope.lockHover = true
					PopupManager.open({
						drawerPaneScope.apply {
							lockHover = false
							showAction = false
						}
					}) {
						if (items != null) {
							items()
							Divider()
						}

						MenuItem("icons/recovery.svg", "恢复默认") {
							drawerPaneScope.upScope.manager.apply {
								size = defaultSize
								splitWeight = defaultSplitWeight
							}
							GlobalScope.launch {
								UIConfig.apply {
									leftDrawerWidth = LeftDrawerManager.size.value
									leftDrawerSplitWeight = LeftDrawerManager.splitWeight
									rightDrawerWidth = RightDrawerManager.size.value
									rightDrawerSplitWeight = RightDrawerManager.splitWeight
									bottomDrawerHeight = BottomDrawerManager.size.value
									bottomDrawerSplitWeight = BottomDrawerManager.splitWeight
								}
							}
						}
						Divider()
						MenuItem("icons/help.svg", "帮助") {}
					}
				}
				DrawerPaneToolBarScope.Action(
					"隐藏", "icons/hide.svg"
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
			Box(contentAlignment = Alignment.Center) {
				JBIcon(icon, 16.dp)
			}
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
			Box(contentAlignment = Alignment.Center) {
				JBIcon(icon, 16.dp)
			}
		}
	}
}