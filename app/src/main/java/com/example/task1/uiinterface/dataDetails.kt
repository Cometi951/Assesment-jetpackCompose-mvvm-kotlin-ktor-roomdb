package com.example.task1

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.task1.Screens.Screens
import com.example.task1.model.User
import com.example.task1.stateData.StateData
import com.example.task1.viewmodel.DataViewModel

@Composable
fun DataDetails (
    navController: NavHostController,
    viewmodel: DataViewModel,
    userId: String,
    userName: String,
    date: String,
    hobbies: String,
    userSessionToken: String
) {

    val context = LocalContext.current
    val state by viewmodel.getSpecificData.collectAsState()
    val scrollState = rememberScrollState()
    var updateUserNM by remember {
        mutableStateOf(userName)
    }
    var updateSession by remember {
        mutableStateOf(userSessionToken)
    }
    var arrHobbies = stringToWords(hobbies) as ArrayList<String>

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.verticalScroll(scrollState)
    ) {

        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Edit Details!",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            fontFamily = FontFamily.Serif,
            color = Color.Blue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp),
            letterSpacing = 5.sp
        )

        Spacer(modifier = Modifier.padding(10.dp))

        TextField(
            value = updateUserNM, onValueChange = {
                updateUserNM = it
            },
            placeholder = {
                Text(text = "Enter Username")
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, start = 25.dp, end = 25.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFFFFFFF)),
            textStyle = TextStyle(
                fontSize = 20.sp,
                color = Color.Blue,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
        )
        //val keyboardController = LocalSoftwareKeyboardController.current

        Spacer(modifier = Modifier.padding(25.dp))

        TextField(
            value = updateSession, onValueChange = {
                updateSession = it
            },
            placeholder = {
                Text(text = "sessionToken")
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, start = 25.dp, end = 25.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFFFFFFF)),
            textStyle = TextStyle(
                fontSize = 20.sp,
                color = Color.Blue,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
        )

        Spacer(modifier = Modifier.padding(25.dp))

        GradientBackground("Update") {

                viewmodel.updateRecord(context, User(
                    userId.toInt(), updateUserNM,
                    date, arrHobbies, updateSession.toInt()))

            navController.navigate(Screens.Main.route)
        }
    }

}

@Composable
fun GradientButton(
    text: String,
    gradient: Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            contentPadding = PaddingValues(),
            onClick = {
                onClick()
            },
        ) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .background(shape = RoundedCornerShape(5.dp), brush = gradient)
                    .then(modifier),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 17.sp,
                    letterSpacing = 5.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun GradientBackground(
    buttonText: String,
    callback: () -> Unit,
) {

    val gradient =
        Brush.horizontalGradient(listOf(Color(0xFF000098), Color(0xFF5A1EFD)))

    Column {
        GradientButton(
            onClick = { callback() },
            text = buttonText.uppercase(),
            gradient = gradient,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
        )
    }
}
fun stringToWords(s : String) = s.trim().replace("[","").replace("]","").split(",")
    .filter { it.isNotEmpty() } // or: .filter { it.isNotBlank() }
    .toList()