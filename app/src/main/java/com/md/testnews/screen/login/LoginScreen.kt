package com.md.testnews.screen.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.md.testnews.R
import com.md.testnews.core.biometric.BiometricHelper
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    helper: BiometricHelper = koinInject()
) {
    val activity = LocalContext.current as FragmentActivity
    val needBiometrics = remember { mutableStateOf(helper.isBiometricAvailable(activity)) }

    fun loginWithFingerprint() {
        helper.authenticateUser(activity) {
            onLogin.invoke()
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (needBiometrics.value) {
            loginWithFingerprint()
        } else {
            //delay for the animation
            delay(2000)
            onLogin.invoke()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val showLogo = remember{ mutableStateOf(false)}
        LaunchedEffect(key1 = Unit) {
            showLogo.value = true
        }
        AnimatedVisibility(
            modifier = Modifier.padding(bottom = 32.dp),
            visible = showLogo.value,
            enter = fadeIn(animationSpec = tween(2000)),
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 48.sp
            )
        }
        if (needBiometrics.value) {
            Button(
                onClick = ::loginWithFingerprint
            ) {
                Text(
                    text = stringResource(id = R.string.login_with_fingerprint)
                )
            }
        }
    }
}
