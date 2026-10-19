package com.example.gureevkurilenko

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            var profileName by remember { mutableStateOf("") }
            var profileSurname by remember { mutableStateOf("") }
            var profilePhone by remember { mutableStateOf("") }
            var profileEmail by remember { mutableStateOf("") }

            NavHost(navController = navController, startDestination = "hello") {

                composable("hello") {
                    HelloScreen(
                        onSignInClick = { navController.navigate("menu") },
                        onExitClick = { finishAffinity() }
                    )
                }

                composable("menu") {
                    MenuScreen(
                        onProfileClick = { navController.navigate("profile") },
                        onSettingsClick = { navController.navigate("settings") },
                        onReportClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:112")
                            }
                            startActivity(intent)
                        },
                        onExitFromMenuClick = { finishAffinity() }
                    )
                }

                composable("profile") {
                    ProfileScreen(
                        name = profileName,
                        surname = profileSurname,
                        phone = profilePhone,
                        email = profileEmail,
                        onEditClick = { navController.navigate("profile_edit") },
                        onBackClick = { navController.navigate("menu") }
                    )
                }

                composable("profile_edit") {
                    ProfileEditScreen(
                        initialName = profileName,
                        initialSurname = profileSurname,
                        initialPhone = profilePhone,
                        initialEmail = profileEmail,
                        onSaveClick = { n, s, p, e ->
                            profileName = n
                            profileSurname = s
                            profilePhone = p
                            profileEmail = e
                            navController.navigate("profile") {
                                popUpTo("profile") { inclusive = true }
                            }
                        },
                        onBackClick = { navController.navigate("profile") }
                    )
                }

                composable("settings") {
                    SettingsScreen(
                        onBackClick = { navController.navigate("menu") }
                    )
                }
            }
        }
    }
}

@Composable
fun HelloScreen(onSignInClick: () -> Unit, onExitClick: () -> Unit) {
    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val isLandscape = this.maxWidth > this.maxHeight

        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.trueniggas),
                    contentDescription = "Logo",
                    modifier = Modifier.size(150.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AuthFields(login, { login = it }, password, { password = it })
                    AuthButtons(onSignInClick, onExitClick)
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.trueniggas),
                    contentDescription = "Logo",
                    modifier = Modifier.size(190.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                AuthFields(login, { login = it }, password, { password = it })
                Spacer(modifier = Modifier.height(32.dp))
                AuthButtons(onSignInClick, onExitClick)
            }
        }
    }
}

@Composable
fun AuthFields(login: String, onLoginChange: (String) -> Unit, password: String, onPasswordChange: (String) -> Unit) {
    val textFieldShape = RoundedCornerShape(32.dp)

    OutlinedTextField(
        value = login,
        onValueChange = onLoginChange,
        label = { Text(stringResource(R.string.login)) },
        shape = textFieldShape,
        modifier = Modifier.fillMaxWidth(0.8f)
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(stringResource(R.string.password)) },
        shape = textFieldShape,
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@Composable
fun AuthButtons(onSignInClick: () -> Unit, onExitClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onSignInClick) { Text(stringResource(R.string.sign_in)) }
        Button(onClick = {}) { Text(stringResource(R.string.sign_up)) }
        Button(onClick = onExitClick) { Text(stringResource(R.string.exit_from_hello)) }
    }
}
@Composable
fun MenuScreen(onProfileClick: () -> Unit, onSettingsClick: () -> Unit, onReportClick: () -> Unit, onExitFromMenuClick: () -> Unit) {
    val menuItems = listOf(
        MenuItem(stringResource(R.string.start), Color(0xFFE91E63), {}),
        MenuItem(stringResource(R.string.profile), Color(0xFF2196F3), onProfileClick),
        MenuItem(stringResource(R.string.settings), Color(0xFF2196F3), onSettingsClick),
        MenuItem(stringResource(R.string.report), Color(0xFFA6A5A5), onReportClick),
        MenuItem(stringResource(R.string.exit), Color(0xFFA6A5A5), onExitFromMenuClick)
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.menu),
            fontSize = 34.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(top = 40.dp, bottom = 24.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(menuItems) { item ->
                Button(
                    onClick = item.onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = item.color),
                    modifier = Modifier.width(200.dp).height(50.dp)
                ) {
                    Text(text = item.text, fontSize = 20.sp)
                }
            }
        }
    }
}

data class MenuItem(val text: String, val color: Color, val onClick: () -> Unit)
@Composable
fun ProfileScreen(name: String, surname: String, phone: String, email: String, onEditClick: () -> Unit, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.profile_profile),
            fontSize = 34.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(top = 40.dp, bottom = 32.dp)
        )

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            ProfileDataRow(stringResource(R.string.name_m), name.ifEmpty { stringResource(R.string.textview_name) })
            ProfileDataRow(stringResource(R.string.surname_m), surname.ifEmpty { stringResource(R.string.textview_surname) })
            ProfileDataRow(stringResource(R.string.number_m), phone.ifEmpty { stringResource(R.string.textviewPhone_number) })
            ProfileDataRow(stringResource(R.string.email_m), email.ifEmpty { stringResource(R.string.textviewemail) })
        }
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = onEditClick, modifier = Modifier.width(200.dp).height(50.dp)) {
            Text(stringResource(R.string.edit_profile_profile), fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA6A5A5)),
            modifier = Modifier.width(200.dp).height(50.dp)
        ) {
            Text(stringResource(R.string.back_from_profile), fontSize = 18.sp)
        }
    }
}
@Composable
fun ProfileDataRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = value, fontSize = 20.sp)
    }
}


@Composable
fun ProfileEditScreen(initialName: String, initialSurname: String, initialPhone: String, initialEmail: String, onSaveClick: (String, String, String, String) -> Unit, onBackClick: () -> Unit) {
    var name by remember { mutableStateOf(initialName) }
    var surname by remember { mutableStateOf(initialSurname) }
    var phone by remember { mutableStateOf(initialPhone) }
    var email by remember { mutableStateOf(initialEmail) }
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.edit_profile),
            fontSize = 34.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(top = 40.dp, bottom = 16.dp)
        )
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text(stringResource(R.string.name)) })
        OutlinedTextField(value = surname, onValueChange = { surname = it }, label = { Text(stringResource(R.string.surname)) })
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text(stringResource(R.string.phone_number)) })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text(stringResource(R.string.email)) })
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { onSaveClick(name, surname, phone, email) }, modifier = Modifier.width(200.dp).height(50.dp)) {
            Text(stringResource(R.string.save), fontSize = 20.sp)
        }
        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA6A5A5)),
            modifier = Modifier.width(200.dp).height(50.dp)
        ) {
            Text(stringResource(R.string.back_form_edit), fontSize = 20.sp)
        }
    }
}


@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    var isDarkTheme by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            fontSize = 34.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(top = 40.dp, bottom = 32.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dark theme", fontSize = 20.sp)
            Switch(checked = isDarkTheme, onCheckedChange = { isDarkTheme = it })
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA6A5A5)),
            modifier = Modifier.width(160.dp).height(50.dp)
        ) {
            Text(stringResource(R.string.back_from_settings), fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

