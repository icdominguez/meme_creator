package com.icdominguez.icdominguez.memecreator.newmeme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icdominguez.icdominguez.memecreator.R

@Composable
fun SaveMemeComponent(
    onSaveMemeClicked: () -> Unit = {},
    onShareMemeClicked: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth().
        padding(bottom = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSaveMemeClicked() }
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 16.dp
                )
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 12.dp),
                painter = painterResource(R.drawable.save_to_files),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Column {
                Text(
                    text = stringResource(R.string.save_to_device_title),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    text = stringResource(R.string.save_to_device_description),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onShareMemeClicked() }
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 16.dp
                )
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 12.dp),
                painter = painterResource(R.drawable.share),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Column {
                Text(
                    text = stringResource(R.string.share_meme_title),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(R.string.share_meme_description),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SaveMemeComponentPreview() {
    SaveMemeComponent()
}