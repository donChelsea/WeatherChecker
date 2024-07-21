package com.example.weatherchecker.presentation.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherchecker.R

@Composable
fun ShowError(
    @StringRes message: Int = R.string.show_error_message,
) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,) {
            Icon(imageVector = Icons.Filled.Error, contentDescription = stringResource(id = R.string.content_description_show_error))

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = stringResource(id = message))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewShowError() {
    ShowError()
}