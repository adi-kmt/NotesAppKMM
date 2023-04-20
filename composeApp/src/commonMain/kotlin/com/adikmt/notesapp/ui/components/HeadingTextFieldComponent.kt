package com.adikmt.notesapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun HeadingTextFieldComponent(
    value: String,
    onValueChanged: (String) -> Unit,
    hint: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    isSingleLine: Boolean = true,
) {
    TextField(
        value = value,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        onValueChange = onValueChanged,
        placeholder = { Text(hint) },
        singleLine = isSingleLine,
        textStyle = textStyle,
        modifier = modifier.fillMaxWidth(),
    )
}
