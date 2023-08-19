package com.eevajonna.catgpt.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eevajonna.catgpt.R
import com.eevajonna.catgpt.data.Message
import com.eevajonna.catgpt.data.Sender
import com.eevajonna.catgpt.ui.components.MessageRow
import com.eevajonna.catgpt.ui.theme.CatGPTTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ChatScreen(
    messages: List<Message>,
    catTyping: Boolean,
    sendMessage: (String) -> Unit,
    deleteMessage: (Message) -> Unit,
) {
    var newMessage by remember { mutableStateOf("") }
    var listState = rememberLazyListState()
    var coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState.layoutInfo.totalItemsCount) {
        coroutineScope.launch {
            listState.animateScrollToItem(index = listState.layoutInfo.totalItemsCount - 1)
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .padding(ChatScreen.padding)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(ChatScreen.chatAreaWeight),
            ) {
                items(messages) { message ->
                    MessageRow(message = message, deleteMessage = deleteMessage)
                }
                item {
                    if (catTyping) {
                        Text(stringResource(R.string.cat_is_typing), modifier = Modifier.padding(bottom = ChatScreen.catTypingBottomPadding))
                    }
                }
            }
            TextFieldAndButtonRow(
                modifier = Modifier.weight(ChatScreen.textFieldWeight),
                value = newMessage,
                onValueChange = { newMessage = it },
                onButtonClick = {
                    sendMessage(newMessage)
                    newMessage = ""
                },
            )
        }
    }
}

@Composable
fun TextFieldAndButtonRow(modifier: Modifier, value: String, onValueChange: (String) -> Unit, onButtonClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(ChatScreen.textFieldWidthFraction),
            label = { Text(stringResource(R.string.write_message)) },
            value = value,
            onValueChange = onValueChange,
            shape = ChatScreen.shape,
        )
        OutlinedIconButton(
            shape = ChatScreen.shape,
            enabled = value.isNotEmpty(),
            onClick = onButtonClick,
        ) {
            Icon(Icons.Filled.Send, stringResource(R.string.send))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    val messages = listOf(

        Message(
            1L,
            text = "Hello",
            sender = Sender.ME,
        ),

        Message(
            2L,
            text = "Meow meow",
            sender = Sender.CAT,
        ),

        Message(
            3L,
            text = "I know hello onetwothreeabc dsgdfg sdfgdsfgdsfgdsf  sdfgdsfdf sdfgdfsert  dsfgsdf et  dfgsdert  sdfgdsfert sdgsddfserte dsgdsf ",
            sender = Sender.ME,
        ),

        Message(
            2L,
            text = "Meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow meow",
            sender = Sender.CAT,
        ),
    )

    CatGPTTheme {
        ChatScreen(messages = messages, false, {}, {})
    }
}

object ChatScreen {
    val shape = RoundedCornerShape(8.dp)
    const val chatAreaWeight = 6f
    const val textFieldWeight = 1f
    const val textFieldWidthFraction = 0.85f
    val padding = 24.dp
    val catTypingBottomPadding = 12.dp
}
