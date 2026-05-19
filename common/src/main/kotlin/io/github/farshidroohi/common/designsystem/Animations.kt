package io.github.farshidroohi.common.designsystem

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.farshidroohi.common.designsystem.pixelperfect.units.pdp

@Composable
fun AnimatedBorderBox(
    modifier: Modifier,
    colors: List<Color>,
    borderWith: Dp = 0.5.pdp,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "gradient_rotation")
    val startAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "startAngle"
    )

    val borderBrush = Brush.linearGradient(colors = colors)

    Surface(
        modifier = modifier, shape = RoundedCornerShape(32.pdp)
    ) {
        Surface(
            modifier = Modifier
                .clipToBounds()
                .fillMaxWidth()
                .padding(2.pdp)
                .drawWithContent {
                    rotate(startAngle) {
                        drawCircle(
                            brush = borderBrush,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                    drawContent()
                }
                .padding(borderWith),

            shape = RoundedCornerShape(40.pdp)
        ) {
            content()
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun Preview_BorderAnimation() {
    Column(
        Modifier
            .padding(all = 16.dp)
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AnimatedBorderBox(
            colors = listOf(Color.Red, Color.Blue, Color.Yellow),
            modifier = Modifier
        ) {
            Text("Hello from animation", modifier = Modifier.padding(8.dp))
        }

    }

}
