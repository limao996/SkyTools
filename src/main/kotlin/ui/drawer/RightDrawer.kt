package ui.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import manager.ui.drawer.RightDrawerManager
import ui.common.HDivider
import utils.boundsMeasure
import utils.boundsRegulate


@Composable
fun RightDrawer() {
	val splitA = rememberSaveable { DrawerContentScope(RightDrawerManager, true) }
	val splitB = rememberSaveable { DrawerContentScope(RightDrawerManager, false) }

	val modifier = if (splitA.isShow || splitB.isShow) Modifier.boundsMeasure(RightDrawerRegulator)
	else Modifier

	if (splitA.isShow || splitB.isShow) {
		Column(
			Modifier
				.fillMaxHeight()
				.width(RightDrawerManager.size)
				.boundsRegulate(RightDrawerSplitRegulator)
				.then(modifier)
		) {
			if (splitA.isShow) Column(
				Modifier
					.fillMaxWidth()
					.weight(1F + RightDrawerManager.splitWeight)
					.boundsMeasure(RightDrawerSplitRegulator)
			) {
				RightDrawerManager.splitA?.invoke(splitA)
			}
			if (splitA.isShow && splitB.isShow) HDivider(RightDrawerSplitRegulator.isActivate)
			if (splitB.isShow) Column(
				Modifier.fillMaxWidth().weight(1F - RightDrawerManager.splitWeight)
			) {
				RightDrawerManager.splitB?.invoke(splitB)
			}
		}
	}
}