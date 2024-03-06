package com.example.myfamily

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.ActivityId
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    // Firebase authentication instance
    private lateinit var auth: FirebaseAuth

    // GoogleSignInClient instance for handling Google Sign-In
    private lateinit var googleSignInClient: GoogleSignInClient

    // Activity result launcher for handling Google Sign-In result
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the result when Google Sign-In is successful
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    // Get GoogleSignInAccount from the task result
                    val account: GoogleSignInAccount? = task.result

                    // Get GoogleSignInAccount's ID token and create Firebase credentials
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)

                    // Sign in with Firebase using Google credentials
                    auth.signInWithCredential(credential).addOnCompleteListener { signInTask ->
                        if (signInTask.isSuccessful) {
                            // If Firebase sign-in is successful, show a success message and navigate to MainActivity
                            Toast.makeText(this, "Sign-in successful", Toast.LENGTH_LONG).show()
                            // storing the value if the user is Logged In
                            SharedPref.putBoolean(SharedPrefConstants.IS_USER_LOGGED_IN, true)

                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            // If Firebase sign-in fails, show an error message
                            Toast.makeText(this, "Sign-in failed", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    // If Google Sign-In task is not successful, show an error message
                    Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_LONG).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase authentication
        auth = Firebase.auth

        // Configure Google Sign-In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id_auth)).requestEmail().build()

        // Initialize GoogleSignInClient with the configured options
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    // Function to initiate Google Sign-In process when the corresponding button is clicked
    fun signIn(view: View) {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
}
