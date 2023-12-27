package utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import component
import manager.ui.drawer.BaseSubDrawerManager
import utils.BoundsRegulator.Direction.Bottom
import utils.BoundsRegulator.Direction.Left
import utils.BoundsRegulator.Direction.Right
import utils.BoundsRegulator.Direction.Top
import java.awt.Cursor

open class BoundsRegulator(
	private val direction: Direction,
	private val boundsWidth: Dp = 8.dp,
	private val offset: Dp = 0.5.dp,
	open val manager: BaseSubDrawerManager,
	open val onHover: ((Boolean) -> Unit) = {},
	open val onDrag: ((Float) -> Boolean) = { false },
	open val onDragStart: (() -> Unit) = {},
	open val onDragEnd: (() -> Unit) = {},
) {
	var bounds = Rect.Zero
	private var lock = false
	private var lastState = false
	private var lastPos = 0F
	private var overflow = 0F

	private val targetCursor = when (direction) {
		Left -> Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR)
		Right -> Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR)
		Top -> Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR)
		Bottom -> Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR)
	}


	fun pointEvent(event: PointerEvent) {
		val pointer = event.changes.first().position
		val pos = if (direction.horizontal == 0) pointer.y else pointer.x
		val offset = if (direction.horizontal == 0) {
			offset.value * direction.vertical
		} else {
			offset.value * direction.horizontal
		}
		val boundsPos = when (direction) {
			Left -> bounds.left
			Right -> bounds.right
			Top -> bounds.top
			Bottom -> bounds.bottom
		}
		val boundsWidth = boundsWidth.value / 2

		val isHover =
			pos > boundsPos - boundsWidth + offset && pos < boundsPos + boundsWidth + offset
		if (isHover && event.type == PointerEventType.Press) {
			lock = true
			onDragStart()
		} else if (lock && event.type == PointerEventType.Release) {
			lock = false
			overflow = 0F
			onDragEnd()
		} else if (lock) {
			if (onDrag(pos - lastPos + overflow)) {
				overflow += pos - lastPos
			} else {
				overflow = 0F
			}
		}

		val isAdjusting = isHover || lock

		if (lastState != isAdjusting) {
			if (isAdjusting) {
				component.cursor = targetCursor
			} else {
				component.cursor = Cursor.getDefaultCursor()
			}
			onHover(isAdjusting)
		}
		lastState = isAdjusting
		lastPos = pos
	}

	enum class Direction(val horizontal: Int, val vertical: Int) {
		Left(-1, 0), Right(1, 0), Top(0, -1), Bottom(0, 1)
	}
}

fun Modifier.boundsMeasure(vararg regulator: BoundsRegulator) =
	if (regulator.isNotEmpty()) onGloballyPositioned { coordinates ->
		for (r in regulator) {
			if (r.manager.isShow) r.bounds = coordinates.boundsInParent()
		}
	} else this


fun Modifier.boundsRegulate(vararg regulator: BoundsRegulator): Modifier {
	if (regulator.isEmpty()) return this

	return pointerInput(Unit) {
		awaitPointerEventScope {
			while (true) {
				val event = awaitPointerEvent()
				for (r in regulator) {
					if (r.manager.isShow)
						r.pointEvent(event)
				}
			}
		}
	}
}