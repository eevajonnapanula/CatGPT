package com.eevajonna.catgpt.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.eevajonna.catgpt.R
import com.eevajonna.catgpt.data.Message
import com.eevajonna.catgpt.data.Sender

@Composable
fun MessageRow(message: Message, deleteMessage: (Message) -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val isMe = message.sender == Sender.ME

    var deleteIsVisible by remember { mutableStateOf(false) }

    fun handleDelete() {
        deleteMessage(message)
        deleteIsVisible = false
        showDeleteDialog = false
    }

    if (showDeleteDialog) {
        DeleteMessageDialog(onDismiss = { showDeleteDialog = false }) {
            handleDelete()
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (text) = createRefs()
        Row(
            Modifier
                .padding(vertical = MessageRow.verticalPadding)
                .constrainAs(text) {
                    when (message.sender) {
                        Sender.CAT -> start.linkTo(parent.start)
                        Sender.ME -> end.linkTo(parent.end)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isMe && deleteIsVisible) {
                DeleteIcon(deleteMessage = { showDeleteDialog = true })
            }
            MessageBlock(
                text = message.text,
                sender = message.sender,
                modifier = Modifier
                    .then(
                        if (isMe) {
                            Modifier.clickable(
                                role = Role.Button,
                                onClickLabel = stringResource(R.string.open_actions),
                            ) {
                                deleteIsVisible = !deleteIsVisible
                            }
                        } else {
                            Modifier
                        },
                    ),
            )
        }
    }
}

@Composable
fun DeleteIcon(modifier: Modifier = Modifier, deleteMessage: () -> Unit) {
    IconButton(onClick = deleteMessage) {
        Icon(Icons.Filled.Delete, stringResource(R.string.delete), modifier = modifier)
    }
}

object MessageRow {
    val verticalPadding = 16.dp
}
