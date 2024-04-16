/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Your Name <your@email.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.nextcloud.client.assistant.taskDetail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextcloud.utils.extensions.getRandomString
import com.owncloud.android.R

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(input: String, output: String, dismiss: () -> Unit) {
    var showInput by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = Modifier.padding(top = 32.dp),
        containerColor = Color.White,
        onDismissRequest = {
            dismiss()
        },
        sheetState = sheetState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorResource(id = R.color.light_grey), shape = RoundedCornerShape(8.dp))
                ) {
                    TextInputSelectButton(
                        Modifier.weight(1f),
                        R.string.assistant_task_detail_screen_input_button_title,
                        showInput,
                        onClick = {
                            showInput = true
                        }
                    )

                    TextInputSelectButton(
                        Modifier.weight(1f),
                        R.string.assistant_task_detail_screen_output_button_title,
                        !showInput,
                        onClick = {
                            showInput = false
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.light_grey), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)) {
                    Text(
                        text = if (showInput) {
                            input
                        } else {
                            output
                        },
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun TextInputSelectButton(modifier: Modifier, titleId: Int, highlightCondition: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = if (highlightCondition) {
            ButtonDefaults.buttonColors(containerColor = Color.White)
        } else {
            ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_grey))
        },
        modifier = modifier
            .widthIn(min = 0.dp, max = 200.dp)
            .padding(horizontal = 4.dp)
    ) {
        Text(text = stringResource(id = titleId), color = Color.Black)
    }
}

@Suppress("MagicNumber")
@Preview
@Composable
private fun TaskDetailScreenPreview() {
    TaskDetailScreen(input = "some input".getRandomString(20), output = "some output".getRandomString(3000)) { }
}
