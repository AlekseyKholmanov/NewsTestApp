package com.example.newstestapp.utils

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleSignInHelper(
    private val activityContext: Context
) {

    var client: GoogleSignInClient? = null

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(activityContext, gso)
    }

    fun getLastAccountSignIn(): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(activityContext)
    }


    fun signIn() {
        val intent = client?.signInIntent
        if (activityContext is Activity) {
            activityContext.startActivityForResult(intent, requestCode)
        }
    }

    fun signOut(){
        client?.signOut()
    }

    companion object {
        val requestCode = Constants.RequestsCode.GoogleSignIn.code
    }

}