package ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.paintWithMarker
import io.kanro.compose.jetbrains.expui.control.themedSvgResource
import io.kanro.compose.jetbrains.expui.style.LocalAreaColors

@Composable
fun JBIcon(
	resource: String,
	size: Dp = 20.dp,
	contentDescription: String? = null,
	modifier: Modifier = Modifier,
	colorFilter: ColorFilter? = null,
	contentScale: ContentScale = ContentScale.Fit,
	alignment: Alignment = Alignment.Center,
	markerColor: Color = Color.Unspecified,
) {
	JBIcon(
		themedSvgResource(resource), size, contentDescription,
		modifier,
		colorFilter,
		contentScale,
		alignment,
		markerColor,
	)
}

@Composable
fun JBIcon(
	bitmap: ImageBitmap,
	size: Dp = 20.dp,
	contentDescription: String? = null,
	modifier: Modifier = Modifier,
	colorFilter: ColorFilter? = null,
	contentScale: ContentScale = ContentScale.Fit,
	alignment: Alignment = Alignment.Center,
	markerColor: Color = Color.Unspecified,
) {
	val painter = remember(bitmap) { BitmapPainter(bitmap) }
	JBIcon(
		painter = painter, size, contentDescription,
		modifier,
		colorFilter,
		contentScale,
		alignment,
		markerColor,
	)
}

@Composable
fun JBIcon(
	imageVector: ImageVector,
	size: Dp = 20.dp,
	contentDescription: String? = null,
	modifier: Modifier = Modifier,
	colorFilter: ColorFilter? = null,
	contentScale: ContentScale = ContentScale.Fit,
	alignment: Alignment = Alignment.Center,
	markerColor: Color = Color.Unspecified,
) {
	JBIcon(
		painter = rememberVectorPainter(imageVector), size, contentDescription,
		modifier,
		colorFilter,
		contentScale,
		alignment,
		markerColor,
	)
}

@Composable
fun JBIcon(
	painter: Painter,
	size: Dp = 20.dp,
	contentDescription: String? = null,
	modifier: Modifier = Modifier,
	colorFilter: ColorFilter? = null,
	contentScale: ContentScale = ContentScale.Fit,
	alignment: Alignment = Alignment.Center,
	markerColor: Color = Color.Unspecified,
) {
	val semantics = if (contentDescription != null) {
		Modifier.semantics {
			this.contentDescription = contentDescription
			this.role = Role.Image
		}
	} else {
		Modifier
	}
	val filter = colorFilter ?: run {
		val foreground = LocalAreaColors.current.foreground
		if (foreground.isSpecified) {
			ColorFilter.tint(foreground)
		} else {
			null
		}
	}
	Layout(
		{},
		modifier
			.size(size)
			.then(semantics)
			.clipToBounds()
			.toolingGraphicsLayer()
			.defaultSizeFor(painter)
			.paintWithMarker(
				painter,
				contentScale = contentScale,
				colorFilter = filter,
				markerColor = markerColor,
				alignment = alignment
			)
	) { _, constraints ->
		layout(constraints.minWidth, constraints.minHeight) {}
	}
}

private fun Size.isInfinite() = width.isInfinite() && height.isInfinite()
private val DefaultIconSizeModifier = Modifier.size(20.dp)
private fun Modifier.defaultSizeFor(painter: Painter) = this.then(
	if (painter.intrinsicSize == Size.Unspecified || painter.intrinsicSize.isInfinite()) {
		DefaultIconSizeModifier
	} else {
		Modifier
	}
)