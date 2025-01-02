package com.example.weatherapp.customui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R

@Composable
fun AppBackground(
   @DrawableRes photoId : Int,
    modifier: Modifier = Modifier) {
    Image(painter = painterResource(id = photoId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .background(Color.Black)
            .alpha(0.7f)
            .blur(
                8.dp,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
        )
}


@Preview
@Composable
private fun AppBackgroundPreview() {
    AppBackground(photoId = R.drawable.flower_background)
}