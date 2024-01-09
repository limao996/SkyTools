package ui.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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

	val rootModifier =
		if (splitA.isShow || splitB.isShow) Modifier.boundsMeasure(LeftDrawerRegulator)
		else Modifier

	val splitModifier =
		if (splitA.isShow && splitB.isShow) Modifier.boundsRegulate(LeftDrawerSplitRegulator)
		else Modifier

	if (splitA.isShow || splitB.isShow) {
		Column(
			Modifier
				.fillMaxHeight()
				.width(LeftDrawerManager.size)
				.then(rootModifier)
				.then(splitModifier)
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
