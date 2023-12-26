package ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.*
import io.kanro.compose.jetbrains.expui.style.LocalAreaColors
import manager.ui.drawer.*
import ui.common.*
import ui.drawer.*
import ui.sidebar.LeftBar
import ui.sidebar.RightBar
import utils.boundsRegulate


@OptIn(ExperimentalFoundationApi::class)
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
								Icon("icons/meetNewUi.svg")
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
								Icon("icons/meetNewUi.svg")
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
								Icon("icons/meetNewUi.svg")
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