package com.chaldrac.twitterclient.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.chaldrac.twitterclient.main.ui.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.twitter.sdk.android.core.*
import com.chaldrac.twitterclient.R
import com.twitter.sdk.android.core.identity.TwitterLoginButton


class LoginActivity : AppCompatActivity() {

    @BindView(R.id.twitterLoginBtn)
    lateinit var twitterLoginButton: TwitterLoginButton
    @BindView(R.id.containerLogin)
    lateinit var container : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        if (TwitterCore.getInstance().sessionManager.activeSession != null){
            navigateToMainScreen()
        }
        
        twitterLoginButton.callback = object : Callback<TwitterSession>(){
            override fun success(result: Result<TwitterSession>?) {
                navigateToMainScreen()
                getFirebaseToken()
            }

            override fun failure(exception: TwitterException?) {
               val msgError = String.format(getString(com.chaldrac.twitterclient.R.string.login_error_message), exception?.localizedMessage)
                Snackbar.make(container, msgError, Snackbar.LENGTH_LONG).show()
            }

        }
    }

    private fun subscribeTopic(){
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                }
                Log.d("TAG", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
        // [END subscribe_topics]
    }

    @SuppressLint("StringFormatInvalid")
    private fun getFirebaseToken(){
        // Get token
        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result!!.token

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d("TAG", msg)
                Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
            })
        // [END retrieve_current_token]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        twitterLoginButton.onActivityResult(requestCode, resultCode, data)
    }

    private fun navigateToMainScreen() {
        startActivity(Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

}
