package com.example.testsapp.ui.composables.functions.radiogroups

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testsapp.R
import com.example.testsapp.ui.composables.functions.custom.ExpandableText

@Composable
fun TypesRadioGroup(selectedOption: String, isError: MutableState<Boolean>, titles: List<String>, descriptions: List<String>): String{
    val selectedOption = remember {
        mutableStateOf(selectedOption)
    }
    val lightGray = colorResource(id = R.color.light_gray)
    val mainOrange = colorResource(id = R.color.main_orange)
    val onSelectionChange = { text: String ->
        selectedOption.value = text
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        titles.forEachIndexed { index, text ->
            val isSelected = {
                text == selectedOption.value
            }
            val textColor = {
                if (isSelected()) mainOrange else lightGray
            }
            Box(modifier = Modifier
                .clickable {
                    onSelectionChange(text)
                //extends with descriptions
            }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = "",
                    enabled = false,
                    readOnly = true,
                    onValueChange = {},
                    placeholder = {
                        Column(verticalArrangement = Arrangement.SpaceBetween){
                            Text(text = text,
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.body2
                            )
                            ExpandableText(isVisible = isSelected(), content = descriptions[index])
                        }
                    },
                    isError = isError.value,
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        placeholderColor = textColor(),
                        cursorColor = colorResource(id = R.color.main_orange),
                        trailingIconColor = colorResource(id = R.color.main_orange),
                        focusedBorderColor = colorResource(id = R.color.light_gray)
                    )
                )
                RadioButton(modifier = Modifier.align(Alignment.CenterEnd),
                    selected = isSelected(),
                    onClick = { onSelectionChange(text) },
                colors = RadioButtonDefaults.colors(selectedColor = colorResource(id = R.color.main_orange)))
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
    return selectedOption.value
}