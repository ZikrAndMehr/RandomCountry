package com.zam.randomcountry.utils.composables

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.zam.randomcountry.R

@Composable
fun MyText(
    @StringRes text: Int,
    modifier: Modifier? = null,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign
) {
    MyText(
        text = stringResource(text),
        modifier = modifier ?: Modifier,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}

@Composable
fun MyText(
    text: String,
    modifier: Modifier? = null,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign
) {
    Text(
        text = text,
        modifier = modifier ?: Modifier,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}

@Preview(showBackground = true)
@Composable
fun MyTextPreview() {
    MyText(
        text = R.string.app_name,
        textAlign = TextAlign.Start
    )
}