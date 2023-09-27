package com.zam.randomcountry.utils.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.zam.randomcountry.R

@Composable
fun MyTextField(
    text: String,
    changed: (String) -> Unit,
    @DrawableRes icon: Int,
    @StringRes hint: Int,
    errorState: Boolean,
    @StringRes errorText: Int?,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = text,
        onValueChange = changed,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = stringResource(hint))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = stringResource(hint)
            )
        },
        trailingIcon = {
            errorText?.let {
                Icon(
                    painter = painterResource(R.drawable.ic_error),
                    contentDescription = stringResource(it),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        supportingText = {
            if (errorState) {
                errorText?.let {
                    Text(
                        text = stringResource(it),
                        Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        isError = errorState,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun MyTextFieldPreview() {
    MyTextField(
        text = stringResource(R.string.app_name),
        changed = {},
        icon = R.drawable.ic_numbers,
        hint = R.string.number,
        errorState = true,
        errorText = R.string.number_range_error,
        keyboardType = KeyboardType.NumberPassword
    )
}