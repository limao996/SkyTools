package manager.ui.drawer

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Immutable

@LayoutScopeMarker
@Immutable
class DrawerScope(private val manager: BaseSubDrawerManager, private val isSplitA: Boolean) {
	val isShow: Boolean
		get() = if (isSplitA) manager.splitA != null else manager.splitB != null

	fun hide() = if (isSplitA) manager.splitA = null else manager.splitB = null

}