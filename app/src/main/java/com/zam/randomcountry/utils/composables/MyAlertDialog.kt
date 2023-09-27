/*
 * Copyright (C) 2023 Zokirjon Mamadjonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zam.randomcountry.utils.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zam.randomcountry.R

@Composable
fun MyAlertDialog(
    @DrawableRes icon: Int,
    @StringRes dialogTitle: Int,
    @StringRes dialogText: Int,
    @StringRes dismissText: Int,
    onDismissClicked: () -> Unit,
    @StringRes confirmText: Int,
    onConfirmClicked: () -> Unit,
) {
    MyAlertDialog(
        icon = icon,
        dialogTitle = stringResource(dialogTitle),
        dialogText = stringResource(dialogText),
        dismissText = dismissText,
        onDismissClicked = onDismissClicked,
        confirmText = confirmText,
        onConfirmClicked = onConfirmClicked
    )
}

@Composable
fun MyAlertDialog(
    @DrawableRes icon: Int,
    dialogTitle: String,
    dialogText: String,
    @StringRes dismissText: Int,
    onDismissClicked: () -> Unit,
    @StringRes confirmText: Int,
    onConfirmClicked: () -> Unit,
) {
    AlertDialog(
        icon = {
               Icon(
                   painter = painterResource(icon),
                   contentDescription = null
               )
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = { onDismissClicked() },
        confirmButton = {
            TextButton(
                onClick = { onConfirmClicked() }
            ) {
                Text(stringResource(confirmText))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissClicked() }
            ) {
                Text(stringResource(dismissText))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyAlertDialogPreview() {
    MyAlertDialog(
        icon = R.drawable.ic_error,
        dialogTitle = stringResource(R.string.error_fetching_data_title),
        dialogText = stringResource(R.string.error_fetching_data_message),
        dismissText = R.string.cancel,
        onDismissClicked = { },
        confirmText = R.string.retry,
        onConfirmClicked = { }
    )
}