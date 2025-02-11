package com.example.secretmessageapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.graphics.Brush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncoderDecoderApp(){
    var inputEncode by remember { mutableStateOf("")}
    var encodedResult by remember { mutableStateOf("")}
    var inputDecode by remember { mutableStateOf("")}
    var decodedResult by remember { mutableStateOf("")}

    val context = LocalContext.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(
                colors = listOf(Color(0xFF294D61), Color(0xFF326580))
            ))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Secret Message App",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White, // Dark text color
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp).shadow(4.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = inputEncode,
            onValueChange = { inputEncode = it },
            keyboardOptions = KeyboardOptions(

                keyboardType = KeyboardType.Text
            ),
            singleLine = true,
            label = { Text("Enter a string to Encode") },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White, // White background for text field
                    shape = RoundedCornerShape(8.dp)
                ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF4CAF50), // Customize the border color
                unfocusedBorderColor = Color(0xFF66BB6A)
            )
                )


        Button(
            onClick = {
                encodedResult = encode(inputEncode)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp), // Rounded corners for button
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF66BB6A), // Green
                contentColor = Color.White,
            )
        ) {
            Text("Encode", fontSize = 18.sp,fontWeight = FontWeight.Bold)

        }

        Row(modifier = Modifier.fillMaxWidth()) {
            SelectionContainer(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Encoded: $encodedResult",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
            }
            if(encodedResult.isNotEmpty()){

                IconButton(onClick = {
                    copyToClipboard(context,encodedResult)

                },modifier = Modifier.padding(top = 12.dp,end = 12.dp)) {Icon(
                        imageVector = Icons.Filled.Share, // Use a copy icon
                        contentDescription = "Copy to Clipboard",
                        tint = Color.Blue // Customize the icon color
                    )

                }
            }








        }


        OutlinedTextField(
            value = inputDecode,
            onValueChange = { inputDecode = it },
            keyboardOptions = KeyboardOptions(

                keyboardType = KeyboardType.Text
            ),
            singleLine = true,
            label = { Text("Enter a string to decode") },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White, // White background for text field
                    shape = RoundedCornerShape(8.dp)
                ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF4CAF50), // Customize the border color
                unfocusedBorderColor = Color(0xFF66BB6A)
            )
        )

        // Button to decode the string
        Button(
            onClick = { decodedResult = decode(inputDecode)},
            modifier = Modifier.fillMaxWidth().shadow(4.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF66BB6A) ,
                contentColor = Color.White,

            )
        ) {
            Text("Decode")
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            SelectionContainer(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Decoded: $decodedResult",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
            }
            if(decodedResult.isNotEmpty()){
                IconButton(onClick = {
                    copyToClipboard(context, decodedResult)
                },modifier = Modifier.padding(top = 12.dp,end = 12.dp)) {Icon(
                        imageVector = Icons.Filled.Share, // Use a copy icon
                        contentDescription = "Copy to Clipboard",
                        tint = Color.Blue // Customize the icon color
                    )

                }
            }
        }


    }

}

fun copyToClipboard(context:Context,text:String){
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("text", text)
    clipboardManager.setPrimaryClip(clipData)
    }




// Function to encode a string
fun encode(input: String): String {
    return input.map { char ->
        (char.code + 2).toChar()
    }.joinToString("")
}

// Function to decode the string
fun decode(input: String): String {
    return input.map { char ->
        (char.code - 2).toChar() // Shift each character back by 2
    }.joinToString("")
}