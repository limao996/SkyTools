package ui.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import manager.ui.drawer.LeftDrawerManager
import ui.common.HDivider
import utils.boundsMeasure
import utils.boundsRegulate


@Composable
fun LeftDrawer() {
	val splitA = rememberSaveable { DrawerContentScope(LeftDrawerManager, true) }
	val splitB = rememberSaveable { DrawerContentScope(LeftDrawerManager, false) }

	if (splitA.isShow || splitB.isShow) {
		val modifier = Modifier.fillMaxHeight().width(LeftDrawerManager.width)
		Column(

			if (!(splitA.isShow && splitB.isShow)) modifier
			else modifier
				.boundsMeasure(LeftDrawerRegulator)
				.boundsRegulate(LeftDrawerSplitRegulator)
		) {
			if (splitA.isShow) Column(
				Modifier
					.fillMaxWidth()
					.weight(1F + LeftDrawerManager.splitWeight)
					.boundsMeasure(LeftDrawerSplitRegulator)
			) {
				LeftDrawerManager.splitA?.invoke(splitA)
			}
			if (splitA.isShow && splitB.isShow) HDivider(
				LeftDrawerSplitRegulator.isActivate
			)
			if (splitB.isShow) Column(
				Modifier.fillMaxWidth().weight(1F - LeftDrawerManager.splitWeight)
			) {
				LeftDrawerManager.splitB?.invoke(splitB)
			}
		}
	}
}
