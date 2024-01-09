package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import ui.common.HDivider
import ui.common.HSubDivider
import ui.common.JBIcon
import ui.common.VDivider
import ui.drawer.*
import ui.infobar.InfoBar
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
					BottomDrawerRegulator,
				)
			) {
				Row(
					Modifier.fillMaxSize().weight(1F).boundsRegulate(
						LeftDrawerRegulator, RightDrawerRegulator,
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
		InfoBar()
	}
}