package com.kttq.attendassist.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.dimensionResource
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.components.AppBodySecondary
import com.kttq.attendassist.core.ui.components.AppButton
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppOutlineTextField
import com.kttq.attendassist.core.ui.components.AppScreenTitle
import com.kttq.attendassist.core.ui.components.AppTextButton

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

        // Normal Text with special font style,
        AppScreenTitle("Sign In")
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_xs)))
        AppOutlineTextField(
            label = "Email",
            value = loginState.email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            leadingIconRes = R.drawable.ic_mail,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_xs)))

        AppOutlineTextField(
            label = "Password",
            value = loginState.password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            leadingIconRes = R.drawable.ic_lock,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isVisible = loginState.isPasswordVisible,
            trailingIcon = {
                val painter = if (loginState.isPasswordVisible) {
                    painterResource(R.drawable.ic_visibility)
                } else {
                    painterResource(R.drawable.ic_visibility_off)
                }
                IconButton(onClick = onPasswordVisibilityChange) {
                    Icon(
                        painter,
                        contentDescription = if (loginState.isPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
        )

        AppTextButton(
            onClick = { /* TODO: Handle forgot password */ },
            content = { AppBodySecondary("Forgot Password?") },
            modifier = Modifier.align(Alignment.End),
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_sm)))

        AppButton(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth(),
            text = { AppLabelPrimary("Log in") }
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_md)))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppBodySecondary("Don't have an account?")
            AppTextButton(
                onClick = { /* TODO: Handle sign up navigation */ },
                content = { AppLabelPrimary("Sign Up") },
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