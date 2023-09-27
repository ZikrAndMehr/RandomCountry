package com.zam.randomcountry.utils.composables.spacer

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zam.randomcountry.R

@Composable
fun MyHorizontalSpacer(@DimenRes space: Int = R.dimen.spacing_single) {
    Spacer(Modifier.size(
        width = dimensionResource(space),
        height = 1.dp
    ))
}

@Preview(showBackground = true)
@Composable
fun MyHorizontalSpacerPreview() {
    MyHorizontalSpacer()
}