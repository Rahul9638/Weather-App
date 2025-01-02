package com.example.weatherapp.utils

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.theme.Typography

val weatherAppTypography = Typography(
    displayLarge = Typography.displayLarge.copy(
        fontFamily = fontFamily, fontSize = 100.sp, color = Color.White
    ),
    displayMedium = Typography.displayMedium.copy(fontFamily = fontFamily, color = Color.White),
    displaySmall = Typography.displaySmall.copy(fontFamily = fontFamily, color = Color.White),
    titleLarge = Typography.titleLarge.copy(fontFamily = fontFamily, color = Color.White),
    titleMedium = Typography.titleMedium.copy(fontFamily = fontFamily, color = Color.White),
    titleSmall = Typography.titleSmall.copy(fontFamily = fontFamily, color = Color.White),
    headlineMedium = Typography.headlineMedium.copy(fontFamily = fontFamily, color = Color.White),
    headlineLarge = Typography.headlineLarge.copy(fontFamily = fontFamily, color = Color.White),
    headlineSmall = Typography.headlineSmall.copy(fontFamily = fontFamily, color = Color.White),
    bodyLarge = Typography.bodyLarge.copy(fontFamily = fontFamily, color = Color.White),
    bodyMedium = Typography.bodyMedium.copy(fontFamily = fontFamily, color = Color.White),
    bodySmall = Typography.bodySmall.copy(fontFamily = fontFamily, color = Color.White),
    labelLarge = Typography.labelLarge.copy(fontFamily = fontFamily, color = Color.White),
    labelSmall = Typography.labelSmall.copy(fontFamily = fontFamily, color = Color.White),
    labelMedium = Typography.labelMedium.copy(fontFamily = fontFamily, color = Color.White),


    )