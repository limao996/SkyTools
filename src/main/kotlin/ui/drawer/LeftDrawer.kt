package ui.drawer

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import manager.ui.drawer.LeftDrawerManager
import ui.common.HDivider


@Composable
fun LeftDrawer() {
	val splitA = rememberSaveable { DrawerContentScope(LeftDrawerManager, true) }
	val splitB = rememberSaveable { DrawerContentScope(LeftDrawerManager, false) }

	val modifier = Modifier.fillMaxHeight().width(LeftDrawerManager.width)
	if (splitA.isShow || splitB.isShow) Column(

		if (!(splitA.isShow && splitB.isShow)) modifier
		else modifier.onGloballyPositioned { coordinates ->
			leftDrawerBounds = coordinates.boundsInParent()
		}.pointerInput(Unit) {
			detectVerticalDragGestures { _, dragAmount ->
				LeftDrawerSplitRegulator.drag(dragAmount)
			}
		}.pointerInput(Unit) {
			awaitPointerEventScope {
				while (true) {
					val event = awaitPointerEvent()
					LeftDrawerSplitRegulator.hover(event)
				}
			}
		}) {
		if (splitA.isShow) Column(Modifier.weight(1F + LeftDrawerManager.splitWeight)) {
			LeftDrawerManager.splitA?.invoke(splitA)
		}
		if (splitA.isShow && splitB.isShow) HDivider(
			LeftDrawerSplitRegulator.lock,
			modifier = Modifier.onGloballyPositioned { coordinates ->
				leftSplitDrawerBounds = coordinates.boundsInParent()
			})
		if (splitB.isShow) Column(Modifier.weight(1F - LeftDrawerManager.splitWeight)) {
			LeftDrawerManager.splitB?.invoke(splitB)
		}
	}
}
