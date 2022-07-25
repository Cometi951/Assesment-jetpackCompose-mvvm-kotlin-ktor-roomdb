package com.example.task1

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.uiinterface.FavouriteMovies
import com.example.moviesapp.uiinterface.HomeScreen
import com.example.moviesapp.uiinterface.MovieInfo
import com.example.task1.Screens.Screens
import com.example.task1.model.User
import com.example.task1.stateData.StateData
import com.example.task1.viewmodel.DataViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewmodel = DataViewModel(this)

        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screens.Main.route) {

                composable(route = Screens.Main.route) {
                    viewmodel.getData(this@MainActivity)
                    FetchData(navController, viewmodel)
                }

                composable(route = Screens.Edit.route +"/{usersId}" + "/{usersName}" +"/{memberDate}" +"/{userHobbies}" + "/{userSessionToken}") {
                    DataDetails(
                        navController,
                        viewmodel,
                        it.arguments?.getString("usersId")!!,
                        it.arguments?.getString("usersName")!!,
                        it.arguments?.getString("memberDate")!!,
                        it.arguments?.getString("userHobbies")!!,
                        it.arguments?.getString("userSessionToken")!!
                    )
                }

                composable(route = Screens.HomeScreen.route) {
                    HomeScreen(
                        viewmodel,
                        navController
                    )
                }
                composable(route = Screens.FavouriteMovies.route) {
                    FavouriteMovies(
                        viewmodel,
                        navController
                    )
                }
                composable(route = Screens.MovieInfo.route + "/{movieid}") {
                    MovieInfo(
                        viewmodel,
                        it.arguments!!.getString("movieid")!!,
                        navController
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FetchData(navController: NavHostController, viewmodel: DataViewModel) {
    val context = LocalContext.current
    val state by viewmodel.getData.collectAsState()

    val defaultArrayData: ArrayList<User> = arrayListOf()

    defaultArrayData.clear()
    defaultArrayData.add(
        User(
            userID = 1,
            userName = "user 1",
            memberDate = date(),
            userHobbies = arrayListOf("reading", "traveling", "writing", "running"),
            sessionToken = 1,
        )
    )
    defaultArrayData.add(
        User(
            userID = 2,
            userName = "user 2",
            memberDate = date(),
            userHobbies = arrayListOf("reading", "traveling", "writing"),
            sessionToken = 2,
        )
    )
    defaultArrayData.add(
        User(
            userID = 3,
            userName = "user 3",
            memberDate = date(),
            userHobbies = arrayListOf("reading", "writing", "running"),
            sessionToken = 3,
        )
    )
    defaultArrayData.add(
        User(
            userID = 4,
            userName = "user 4",
            memberDate = date(),
            userHobbies = arrayListOf("traveling", "writing", "running", "movies"),
            sessionToken = 4,
        )
    )
    defaultArrayData.add(
        User(
            userID = 5,
            userName = "user 5",
            memberDate = date(),
            userHobbies = arrayListOf("reading", "traveling", "running", "movies"),
            sessionToken = 5,
        )
    )
    defaultArrayData.add(
        User(
            userID = 6,
            userName = "user 6",
            memberDate = date(),
            userHobbies = arrayListOf("writing", "running"),
            sessionToken = 6,
        )
    )
    defaultArrayData.add(
        User(
            userID = 7,
            userName = "user 7",
            memberDate = date(),
            userHobbies = arrayListOf("reading", "traveling", "writing", "movies"),
            sessionToken = 7,
        )
    )
    defaultArrayData.add(
        User(
            userID = 8,
            userName = "user 8",
            memberDate = date(),
            userHobbies = arrayListOf("reading", "writing", "running", "drawing"),
            sessionToken = 8,
        )
    )

    defaultArrayData.add(
        User(
            userID = 9,
            userName = "uder 9",
            memberDate = date(),
            userHobbies = arrayListOf("reading", "writing", "running", "drawing"),
            sessionToken = 8,
        )
    )

    defaultArrayData.add(
        User(
            userID = 10,
            userName = "user 10",
            memberDate = date(),
            userHobbies = arrayListOf("reading", "writing", "running", "drawing"),
            sessionToken = 9,
        )
    )

    when (state) {
        StateData.START -> {

        }
        StateData.LOADING -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        is StateData.SUCCESS -> {
            val users = (state as StateData.SUCCESS).users as ArrayList<User>
            if (users.isNotEmpty()) {
                UserListScreen(navController, viewmodel, users)
            } else {

                viewmodel.insertData(context, defaultArrayData)
                viewmodel.getData(context)
            }

        }
        is StateData.FAILURE -> {
            viewmodel.insertData(context, defaultArrayData)
            viewmodel.getData(context)


        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalUnitApi::class)
@Composable
fun UserListScreen(navController: NavController, viewmodel: DataViewModel, users: List<User>) {
    val context = LocalContext.current
    val state by viewmodel.getSpecificData.collectAsState()

    Column() {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .shadow(1.dp, RectangleShape)
                .padding(vertical = 10.dp, horizontal = 25.dp)
        ) {

            Text(
                text = " Home screen",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp
            )

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                IconButton(modifier = Modifier.background(Color.Black),onClick = { navController.navigate(Screens.HomeScreen.route) }) {
                    Text(text = "Task 2", color = Color.White)
                }

            }
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                count = users.size,
                itemContent = {

                    val dismissState = rememberDismissState()

                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {

                        viewmodel.deleteRecord(context, users[it].userID)
                        viewmodel.getData(context)

                    } else if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
                        viewmodel.getUserIdData(context, users[it].userID)

                        when(state){
                            is StateData.START -> {

                            }
                            is StateData.LOADING -> {

                            }
                            is StateData.SUCCESS -> {
                                val getUsersDetails = (state as StateData.SUCCESS).users as ArrayList<User>

                                val userId = getUsersDetails[0].userID
                                val userName = getUsersDetails[0].userName
                                val date = getUsersDetails[0].memberDate
                                val hobbies = getUsersDetails[0].userHobbies
                                val sessionToken = getUsersDetails[0].sessionToken
                                navController.navigate(Screens.Edit.route +"/${userId}" +"/${userName}" +"/${date}" +"/${hobbies}" +"/${sessionToken}")
                            }
                            is StateData.FAILURE -> {

                            }
                        }


                        /*var update = users[it]
                        update.userName = Random(10).toString()
                        viewmodel.updateRecord(context,update)
                        navController.navigate(Screens.Main.route)*/
                    }
                    SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier
                            .padding(vertical = Dp(1f)),
                        directions = setOf(
                            DismissDirection.EndToStart,
                            DismissDirection.StartToEnd
                        ),
                        dismissThresholds = { direction ->
                            FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                            FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.1f else 0.05f)
                        },
                        background = {
                            var icon = Icons.Default.Delete
                            var alignment = Alignment.CenterEnd
                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.Default -> Color.White
                                    DismissValue.DismissedToEnd -> {
                                        alignment = Alignment.CenterStart; icon =
                                            Icons.Default.Edit; Color.Cyan
                                    }
                                    DismissValue.DismissedToStart -> {
                                        alignment = Alignment.CenterEnd; icon =
                                            Icons.Default.Delete; Color.Red
                                    }
                                }
                            )


                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                            )

                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = Dp(20f)),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = "Icon",
                                    modifier = Modifier.scale(scale)
                                )
                            }
                        },
                        dismissContent = {
                            Spacer(modifier = Modifier.padding(top = 15.dp, bottom = 15.dp))
                            Card(modifier = Modifier.clickable {

                            }) {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .align(Alignment.CenterVertically)
                                    ) {
                                        Text(text = users[it].userName, style = typography.h6)
                                        Text(text = "Swipe To Edit And Delete", style = typography.caption)
                                    }
                                }
                            }
                        }
                    )
                }
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun date(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatted = current.format(formatter)

    return formatted
}