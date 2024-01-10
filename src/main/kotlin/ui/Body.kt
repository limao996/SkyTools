package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.kanro.compose.jetbrains.expui.style.LocalAreaColors
import ui.common.HDivider
import ui.common.VDivider
import ui.drawer.*
import ui.infobar.InfoBar
import ui.sidebar.LeftBar
import ui.sidebar.RightBar
import ui.tabpage.TabPage
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
						TabPage()
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