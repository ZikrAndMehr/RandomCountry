package com.zam.randomcountry.utils.composables

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.zam.randomcountry.R

@Composable
fun MyCircularProgressIndicator(
    @DimenRes size: Int = R.dimen.spacing_quintuple,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    trackColor: Color = MaterialTheme.colorScheme.secondary,
) {
    CircularProgressIndicator(
        modifier = Modifier.size(dimensionResource(size)),
        color = color,
        trackColor = trackColor
    )
}

@Preview(showBackground = true)
@Composable
fun MyCircularProgressIndicatorPreview() {
    MyCircularProgressIndicator()
}