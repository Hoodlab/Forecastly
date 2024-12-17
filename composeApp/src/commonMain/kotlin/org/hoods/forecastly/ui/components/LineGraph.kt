package org.hoods.forecastly.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun <T> LineGraph(
    dataPoints: List<T>, // Generic list of data
    xValueMapper: (T) -> String, // Maps data to X-axis labels
    yValueMapper: (T) -> Float, // Maps data to Y-axis values
    modifier: Modifier = Modifier,
    graphTitle: String = "",
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    labelFontSize: TextUnit = 12.sp,
    gradientColors: List<Color> = listOf(
        MaterialTheme.colorScheme.surfaceContainerHighest,
        Color.Transparent
    ),
) {
    val padding = 16.dp
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier.padding(padding)){
        val maxWidth = size.width
        val maxHeight = size.height

        // Adjust padding for labels
        val xAxisPadding = 60f // Space for X-axis labels
        val yAxisPadding = 80f // Space for Y-axis labels
        val graphPadding = 50f // Additional graph padding
        val graphWidth = maxWidth - yAxisPadding - graphPadding
        val graphHeight = maxHeight - xAxisPadding - graphPadding

        // Determine the range of Y values
        val yValues = dataPoints.map(yValueMapper)
        val maxYValue = yValues.maxOrNull() ?: 0f
        val minYValue = yValues.minOrNull() ?: 0f
        val yRange = maxYValue - minYValue

        // Map data to graph coordinates
        val xInterval = graphWidth / (dataPoints.size - 1).coerceAtLeast(1)
        val yUnit = if (yRange > 0) graphHeight / yRange else 0f
        // Plot points and lines
        val points = dataPoints.mapIndexed { index, data ->
            Offset(
                x = yAxisPadding + index * xInterval,
                y = maxHeight - graphPadding - (yValueMapper(data) - minYValue) * yUnit
            )
        }
        // Draw gradient fill
        val path = Path().apply {
            points.forEachIndexed { index, point ->
                if (index == 0) moveTo(point.x, maxHeight - xAxisPadding) // Start at the bottom
                lineTo(point.x, point.y) // Line to the data point
            }
            lineTo(points.last().x, maxHeight - xAxisPadding) // Close the path at the bottom
            close()
        }
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = gradientColors,
                startY = maxHeight - xAxisPadding,
                endY = graphPadding
            )
        )
        points.zipWithNext { start, end ->
            drawLine(
                color = color,
                start = start,
                end = end,
                strokeWidth = 3f
            )
        }

        // Draw X-Axis labels
        val labelStep = dataPoints.size / 6 // Adjust label frequency
        dataPoints.forEachIndexed { index, data ->
            val xPosition = yAxisPadding + index * xInterval
            val labelOffset =
                Offset(xPosition - labelFontSize.value / 2, maxHeight - xAxisPadding + 10f)
            if (index % labelStep == 0) {
                drawText(
                    textMeasurer = textMeasurer,
                    text = xValueMapper(data),
                    topLeft = labelOffset,
                    style = TextStyle(color = color)
                )
            }
        }

        // Draw Y-Axis labels
        val yLabelStep = yRange / 5 // Divide Y-axis into 5 steps
        for (i in 0..5) {
            val yValue = minYValue + i * yLabelStep
            val yPosition = maxHeight - xAxisPadding - i * (graphHeight / 5)
            drawText(
                textMeasurer = textMeasurer,
                text = yValue.roundToInt().toString(),
                topLeft = Offset(
                    yAxisPadding / 2 - labelFontSize.value,
                    yPosition - labelFontSize.value / 2
                ),
                style = TextStyle(color = color)
            )

        }

        // Optionally draw the graph title
        if (graphTitle.isNotEmpty()) {
            drawText(
                textMeasurer = textMeasurer,
                text = graphTitle,
                topLeft = Offset(
                    maxWidth / 2 - textMeasurer.measure(graphTitle).size.width / 2,
                    graphPadding / 2
                ),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = (labelFontSize * 1.2), color = color)
            )
        }

    }

}