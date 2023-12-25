package ui.drawer

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import manager.ui.drawer.DrawerScope
import manager.ui.drawer.RightDrawerManager
import ui.common.HDivider


@Composable
fun RightDrawer() {
	val splitA = rememberSaveable { DrawerScope(RightDrawerManager, true) }
	val splitB = rememberSaveable { DrawerScope(RightDrawerManager, false) }

	val modifier = Modifier.fillMaxHeight().width(RightDrawerManager.width)
	if (splitA.isShow || splitB.isShow) Column(
		if (!(splitA.isShow && splitB.isShow)) modifier
		else modifier.onGloballyPositioned { coordinates ->
			rightDrawerBounds = coordinates.boundsInParent()
		}.pointerInput(Unit) {
			detectVerticalDragGestures { _, dragAmount ->
				RightDrawerSplitRegulator.drag(dragAmount)
			}
		}.pointerInput(Unit) {
			awaitPointerEventScope {
				while (true) {
					val event = awaitPointerEvent()
					RightDrawerSplitRegulator.hover(event)
				}
			}
		},
	) {
		if (splitA.isShow) Column(Modifier.weight(1F + RightDrawerManager.splitWeight)) {
			RightDrawerManager.splitA?.invoke(splitA)
		}
		if (splitA.isShow && splitB.isShow) HDivider(RightDrawerSplitRegulator.lock,
			modifier = Modifier.onGloballyPositioned { coordinates ->
				rightSplitDrawerBounds = coordinates.boundsInParent()
			})
		if (splitB.isShow) Column(Modifier.weight(1F - RightDrawerManager.splitWeight)) {
			RightDrawerManager.splitB?.invoke(splitB)
		}
	}
}

