package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.*
import io.kanro.compose.jetbrains.expui.style.LocalAreaColors
import manager.ui.drawer.*
import ui.common.*
import ui.drawer.*
import ui.sidebar.LeftBar
import ui.sidebar.RightBar


@Composable
fun Body() {
	Column(Modifier.fillMaxSize()) {
		Row(Modifier.fillMaxSize().weight(1F)) {
			//左栏
			LeftBar()
			VDivider()
			Column(Modifier.fillMaxSize().weight(1F).pointerInput(Unit) {
				detectVerticalDragGestures { _, dragAmount ->
					if (BottomDrawerManager.isShow) BottomDrawerRegulator.drag(
						dragAmount
					)
				}
			}.pointerInput(Unit) {
				awaitPointerEventScope {
					while (true) {
						val event = awaitPointerEvent()
						if (BottomDrawerManager.isShow) BottomDrawerRegulator.hover(
							event
						)
					}
				}
			}) {
				Row(Modifier.fillMaxSize().weight(1F).pointerInput(Unit) {
					detectHorizontalDragGestures { _, dragAmount ->
						if (LeftDrawerManager.isShow) LeftDrawerRegulator.drag(
							dragAmount
						)
						if (RightDrawerManager.isShow) RightDrawerRegulator.drag(
							dragAmount
						)
					}
				}.pointerInput(Unit) {
					awaitPointerEventScope {
						while (true) {
							val event = awaitPointerEvent()
							if (LeftDrawerManager.isShow) LeftDrawerRegulator.hover(
								event
							)
							if (RightDrawerManager.isShow) RightDrawerRegulator.hover(
								event
							)
						}
					}
				}) {
					//左抽屉
					LeftDrawer()
					VDivider(LeftDrawerRegulator.lock)
					//标签页
					Column(
						Modifier
							.fillMaxSize()
							.weight(1F)
							.background(LocalAreaColors.current.startBorderColor),
					) {
						Row(Modifier.height(40.dp).selectableGroup()) {
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
					VDivider(RightDrawerRegulator.lock)
					RightDrawer()
				}
				//下抽屉
				HDivider(BottomDrawerRegulator.lock)
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