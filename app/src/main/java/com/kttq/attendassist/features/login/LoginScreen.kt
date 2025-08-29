package com.kttq.attendassist.features.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.dimensionResource
import com.kttq.attendassist.R

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    LoginScreen(
        loginViewModel.loginUIInfo.collectAsState().value,
        loginViewModel::login,
        loginViewModel::onEmailChanged,
        loginViewModel::onPasswordChanged,
        loginViewModel::onPasswordVisibilityChanged,
        modifier
    )
}

@Composable
fun LoginScreen(
    loginState: LoginUIInfo,
    onLogin: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // // Logo / Icon placeholder
        // Box(
        //     modifier = Modifier
        //         .padding(bottom = dimensionResource(R.dimen.padding_medium)),
        //     contentAlignment = Alignment.Center
        // ) {
        //     Icon(
        //         painter = painterResource(android.R.drawable.ic_menu_manage), // Replace with your logo
        //         contentDescription = "App Logo",
        //         tint = Color.Gray,
        //         modifier = Modifier.size(dimensionResource(R.dimen.avatar_lg))
        //     )
        // }

        Text(
            text = "Sign in",
            style = MaterialTheme.typography.headlineMedium
                .copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_xs)))

        // Text(
        //     text = "Sign in to continue to your account",
        //     style = MaterialTheme.typography.bodyMedium,
        //     textAlign = TextAlign.Center
        // )

        // Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_xl)))

        // Email Field
        OutlinedTextField(
            value = loginState.email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            leadingIcon = {
                Icon(painterResource(R.drawable.ic_mail), contentDescription = null)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_xs)))

        // Password Field
        OutlinedTextField(
            value = loginState.password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            leadingIcon = {
                Icon(painterResource(R.drawable.ic_lock), contentDescription = null)
            },
            trailingIcon = {
                val painter = if (loginState.isPasswordVisible) {
                    painterResource(R.drawable.ic_visibility)
                } else {
                    painterResource(R.drawable.ic_visibility_off)
                }
                IconButton(onClick = onPasswordVisibilityChange ) {
                    Icon(
                        painter,
                        contentDescription = if (loginState.isPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (loginState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_xs)))

        // Forgot password
        Text(
            text = "Forgot password?",
            style = MaterialTheme.typography.bodySmall
                .copy(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    // What to do when clicked?
                }
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_sm)))

        // Sign In button
        Button(
            onClick = onLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.button_height)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Sign In", fontSize = dimensionResource(R.dimen.text_xl).value.sp)
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_md)))

        // Sign up link
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Don’t have an account? ")
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.bodyMedium
                    .copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.clickable { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            LoginUIInfo("", ""), {},
            {}, {}, {}
        )
    }
}