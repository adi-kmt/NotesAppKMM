package com.adikmt.notesapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle

@Composable
fun HeadingTextFieldComponent(
    text: String,
    hint: String,
    isHintVisible: Boolean,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    isSingleLine: Boolean = true,
    onFocusChanged: (FocusState) -> Unit
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValueChanged,
            singleLine = isSingleLine,
            textStyle = textStyle,
            modifier = Modifier.onFocusChanged {
                onFocusChanged(it)
            }
                .fillMaxWidth()
        )
        if (isHintVisible) {
            Text(hint)
        }
    }

}