package ui.drawer

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Immutable
import manager.ui.drawer.BaseSubDrawerManager

@LayoutScopeMarker
@Immutable
class DrawerContentScope(val manager: BaseSubDrawerManager, val isSplitA: Boolean) {
	val isShow: Boolean
		get() = if (isSplitA) manager.splitA != null else manager.splitB != null

	fun hide() = if (isSplitA) manager.splitA = null else manager.splitB = null

}