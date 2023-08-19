package com.eevajonna.catgpt.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eevajonna.catgpt.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteMessageDialog(onDismiss: () -> Unit, positiveAction: () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation,
        ) {
            Column(modifier = Modifier.padding(DeleteMessageDialog.padding)) {
                Text(stringResource(R.string.are_you_sure_you_want_to_delete_that_message))
                Spacer(modifier = Modifier.height(DeleteMessageDialog.spacerHeight))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = { onDismiss() }) {
                        Text(stringResource(R.string.no))
                    }
                    Button(onClick = {
                        positiveAction()
                    }) {
                        Text(stringResource(R.string.yes))
                    }
                }
            }
        }
    }
}

object DeleteMessageDialog {
    val padding = 16.dp
    val spacerHeight = 24.dp
}
