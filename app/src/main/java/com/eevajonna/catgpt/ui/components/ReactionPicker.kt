package com.eevajonna.catgpt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.eevajonna.catgpt.data.Message
import com.eevajonna.catgpt.data.Reaction
import com.eevajonna.catgpt.data.Sender
import com.eevajonna.catgpt.ui.screens.ChatScreen
import com.eevajonna.catgpt.ui.theme.CatGPTTheme

@Composable
fun ReactionPicker(message: Message, yPosition: Dp, onReactionSelect: (message: Message) -> Unit, onDismiss: () -> Unit) {
    fun changeReaction(reaction: Reaction) {
        onReactionSelect(message.copy(reaction = reaction))
        onDismiss()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.5f))
                .clickable { onDismiss() },
        )
        ConstraintLayout(modifier = Modifier.positionReactionsPicker(yPosition)) {
            val (reactions, messageRow) = createRefs()
            MessageBlock(
                text = message.text,
                sender = message.sender,
                modifier = Modifier
                    .constrainAs(messageRow) {
                        when (message.sender) {
                            Sender.ME -> end.linkTo(parent.end, ReactionPicker.messageBlockHorizontalPadding)
                            Sender.CAT -> start.linkTo(parent.start, ReactionPicker.messageBlockHorizontalPadding)
                        }
                        top.linkTo(parent.top, ReactionPicker.messageBlockTopPadding)
                    },
            )
            Reactions(
                onClick = ::changeReaction,
                modifier = Modifier.constrainAs(reactions) {
                    when (message.sender) {
                        Sender.CAT -> start.linkTo(messageRow.start)
                        Sender.ME -> end.linkTo(messageRow.end)
                    }

                    bottom.linkTo(messageRow.top, ReactionPicker.reactionsBottomPadding)
                },
            )
        }
    }
}

@Composable
fun Reactions(modifier: Modifier = Modifier, onClick: (Reaction) -> Unit) {
    Row(
        modifier = modifier
            .clip(ChatScreen.shape)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.spacedBy(ReactionPicker.Reactions.spacedBy),
    ) {
        ReactionButton(Reaction.GRINNING_CAT, onClick)
        ReactionButton(Reaction.GRINNING_CAT_WITH_SMILING_EYES, onClick)
        ReactionButton(Reaction.WEARY_CAT, onClick)
        ReactionButton(Reaction.SMILING_CAT_WITH_HEART_EYES, onClick)
    }
}

@Composable
fun ReactionButton(reaction: Reaction, onClick: (Reaction) -> Unit) {
    IconButton(onClick = { onClick(reaction) }) {
        Text(reaction.emoji, style = MaterialTheme.typography.headlineLarge)
    }
}

fun Modifier.positionReactionsPicker(yPosition: Dp) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    layout(placeable.width, placeable.height) {
        placeable.place(0, yPosition.roundToPx())
    }
}

@Preview(showSystemUi = true)
@Composable
fun ReactionPickerPreview() {
    CatGPTTheme {
        ReactionPicker(
            message = Message(
                text = "Hello this is a long, long. long. long long long day and message",
                sender = Sender.ME,
            ),
            yPosition = 1233.dp,
            onReactionSelect = {},
            onDismiss = {},
        )
    }
}

object ReactionPicker {
    val messageBlockHorizontalPadding = 24.dp
    val messageBlockTopPadding = 8.dp
    val reactionsBottomPadding = 12.dp

    object Reactions {
        val spacedBy = 8.dp
    }
}
