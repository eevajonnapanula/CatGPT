package com.eevajonna.catgpt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.eevajonna.catgpt.data.Sender
import com.eevajonna.catgpt.ui.screens.ChatScreen

@Composable
fun MessageBlock(modifier: Modifier = Modifier, text: String, sender: Sender) {
    val color = when (sender) {
        Sender.ME -> MaterialTheme.colorScheme.primaryContainer
        Sender.CAT -> MaterialTheme.colorScheme.tertiaryContainer
    }

    val textColor = when (sender) {
        Sender.ME -> MaterialTheme.colorScheme.onPrimaryContainer
        Sender.CAT -> MaterialTheme.colorScheme.onTertiaryContainer
    }

    Text(
        text = text,
        color = textColor,
        modifier = modifier
            .widthIn(MessageBlock.minWidth, MessageBlock.maxWidth)
            .clip(ChatScreen.shape)
            .background(color)
            .padding(MessageBlock.padding),
    )
}

object MessageBlock {
    val minWidth = 0.dp
    val maxWidth = 240.dp
    val padding = 12.dp
}
