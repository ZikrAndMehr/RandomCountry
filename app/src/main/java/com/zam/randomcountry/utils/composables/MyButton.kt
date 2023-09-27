package com.zam.randomcountry.utils.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zam.randomcountry.R
import com.zam.randomcountry.utils.extensions.conditional

@Composable
fun MyButton(
    @StringRes text: Int,
    clicked: () -> Unit,
    maxWidth: Boolean = false
) {
    Button(
        onClick = clicked,
        modifier = Modifier.conditional(maxWidth) { fillMaxWidth() }
    ) {
        Text(text = stringResource(text))
    }
}

@Preview(showBackground = true)
@Composable
fun MyButtonPreview() {
    MyButton(
        text = R.string.app_name,
        clicked = { }
    )
}