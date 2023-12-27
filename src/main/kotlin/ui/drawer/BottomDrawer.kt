package ui.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import manager.ui.drawer.BottomDrawerManager
import ui.common.VDivider
import utils.boundsMeasure
import utils.boundsRegulate


@Composable
fun BottomDrawer() {
	val splitA = rememberSaveable { DrawerContentScope(BottomDrawerManager, true) }
	val splitB = rememberSaveable { DrawerContentScope(BottomDrawerManager, false) }

	val modifier = Modifier.fillMaxWidth().height(BottomDrawerManager.size)
	if (splitA.isShow || splitB.isShow) Row(
		if (!(splitA.isShow && splitB.isShow)) modifier
		else modifier
			.boundsMeasure(BottomDrawerRegulator)
			.boundsRegulate(BottomDrawerSplitRegulator),
	) {
		if (splitA.isShow) Column(
			Modifier
				.fillMaxHeight()
				.weight(1F + BottomDrawerManager.splitWeight)
				.boundsMeasure(BottomDrawerSplitRegulator)
		) {
			BottomDrawerManager.splitA?.invoke(splitA)
		}
		if (splitA.isShow && splitB.isShow) VDivider(BottomDrawerSplitRegulator.isActivate)
		if (splitB.isShow) Column(
			Modifier.fillMaxHeight().weight(1F - BottomDrawerManager.splitWeight)
		) {
			BottomDrawerManager.splitB?.invoke(splitB)
		}
	}
}

