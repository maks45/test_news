package com.md.testnews.core.biometric

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity


class BiometricHelper {

    fun isBiometricAvailable(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> {
                Log.e("TAG", "Biometric not available")
                false
            }
        }
    }

    private fun getBiometricPrompt(
        context: FragmentActivity,
        onAuthSucceed: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt {
        val biometricPrompt =
            BiometricPrompt(
                context,
                ContextCompat.getMainExecutor(context),
                object : BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult
                    ) {
                        onAuthSucceed(result)
                    }
                }
            )
        return biometricPrompt
    }

    fun authenticateUser(context: FragmentActivity, onSuccess: () -> Unit) {
        val biometricPrompt =
            getBiometricPrompt(context) { _ ->
                onSuccess()
            }
        biometricPrompt.authenticate(
            BiometricPrompt.PromptInfo.Builder()
                .setTitle("Scan your fingerprint to open the app")
                .setNegativeButtonText("Cancel")
                .build()
        )
    }
}
