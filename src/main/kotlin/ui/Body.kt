package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.CloseableTab
import io.kanro.compose.jetbrains.expui.control.Label
import io.kanro.compose.jetbrains.expui.style.LocalAreaColors
import manager.ui.drawer.BottomDrawerManager
import manager.ui.drawer.LeftDrawerManager
import manager.ui.drawer.RightDrawerManager
import ui.common.HDivider
import ui.common.HSubDivider
import ui.common.JBIcon
import ui.common.VDivider
import ui.drawer.*
import ui.sidebar.LeftBar
import ui.sidebar.RightBar
import utils.boundsRegulate


@Composable
fun Body() {
	Column(Modifier.fillMaxSize()) {
		Row(Modifier.fillMaxSize().weight(1F)) {
			//左栏
			LeftBar()
			VDivider()
			Column(
				Modifier.fillMaxSize().weight(1F).boundsRegulate(
					if (BottomDrawerManager.isShow) BottomDrawerRegulator else null,
				)
			) {
				Row(
					Modifier.fillMaxSize().weight(1F).boundsRegulate(
						if (LeftDrawerManager.isShow) LeftDrawerRegulator else null,
						if (RightDrawerManager.isShow) RightDrawerRegulator else null,
					)
				) {
					//左抽屉
					LeftDrawer()
					VDivider(LeftDrawerRegulator.isActivate)
					//标签页
					Column(
						Modifier
							.fillMaxSize()
							.weight(1F)
							.background(LocalAreaColors.current.startBorderColor),
					) {
						Row(
							Modifier.height(40.dp).selectableGroup().horizontalScroll(
								rememberScrollState()
							)
						) {
							var selected by remember { mutableStateOf(0) }
							CloseableTab(
								selected == 0,
								{
									selected = 0
								},
								{},
								modifier = Modifier
									.fillMaxHeight()
									.align(Alignment.CenterVertically)
							) {
								JBIcon("icons/meetNewUi.svg", 16.dp)
								Label("First")
							}
							CloseableTab(
								selected == 1,
								{
									selected = 1
								},
								{},
								modifier = Modifier
									.fillMaxHeight()
									.align(Alignment.CenterVertically)
							) {
								JBIcon("icons/meetNewUi.svg", 16.dp)
								Label("Second")
							}
							CloseableTab(
								selected == 2,
								{
									selected = 2
								},
								{},
								modifier = Modifier
									.fillMaxHeight()
									.align(Alignment.CenterVertically)
							) {
								JBIcon("icons/meetNewUi.svg", 16.dp)
								Label("Third")
							}
						}
						HSubDivider(false)
					}
					//右抽屉
					VDivider(RightDrawerRegulator.isActivate)
					RightDrawer()
				}
				//下抽屉
				HDivider(BottomDrawerRegulator.isActivate)
				BottomDrawer()
			}
			//右栏
			VDivider()
			RightBar()
		}
		//信息栏
		HDivider()
		Row(
			Modifier.fillMaxWidth().height(30.dp).padding(horizontal = 4.dp),
			horizontalArrangement = Arrangement.spacedBy(10.dp),
			verticalAlignment = Alignment.CenterVertically
		) {}
	}
}