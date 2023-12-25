package ui.drawer

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import manager.ui.drawer.BottomDrawerManager
import manager.ui.drawer.DrawerScope
import ui.common.VDivider


@Composable
fun BottomDrawer() {
	val splitA = rememberSaveable { DrawerScope(BottomDrawerManager, true) }
	val splitB = rememberSaveable { DrawerScope(BottomDrawerManager, false) }

	val modifier = Modifier.fillMaxWidth().height(BottomDrawerManager.height)
	if (splitA.isShow || splitB.isShow) Row(
		if (!(splitA.isShow && splitB.isShow)) modifier
		else modifier.onGloballyPositioned { coordinates ->
			bottomDrawerBounds = coordinates.boundsInParent()
		}.pointerInput(Unit) {
			detectHorizontalDragGestures { _, dragAmount ->
				BottomDrawerSplitRegulator.drag(dragAmount)
			}
		}.pointerInput(Unit) {
			awaitPointerEventScope {
				while (true) {
					val event = awaitPointerEvent()
					BottomDrawerSplitRegulator.hover(event)
				}
			}
		},
	) {
		if (splitA.isShow) Column(
			Modifier.fillMaxHeight().weight(1F + BottomDrawerManager.splitWeight)
		) {
			BottomDrawerManager.splitA?.invoke(splitA)
		}
		if (splitA.isShow && splitB.isShow) VDivider(BottomDrawerSplitRegulator.lock,
			modifier = Modifier.onGloballyPositioned { coordinates ->
				bottomSplitDrawerBounds = coordinates.boundsInParent()
			})
		if (splitB.isShow) Column(
			Modifier.fillMaxHeight().weight(1F - BottomDrawerManager.splitWeight)
		) {
			BottomDrawerManager.splitB?.invoke(splitB)
		}
	}
}

