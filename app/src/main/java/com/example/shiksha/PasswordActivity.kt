package com.example.shiksha

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class PasswordActivity : AppCompatActivity() {

    private var passwordEmail: EditText? = null
    private var resetPassword: Button? = null
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        passwordEmail =
            findViewById<View>(R.id.etPasswordEmail) as EditText
        resetPassword =
            findViewById<View>(R.id.btnPasswordReset) as Button
        firebaseAuth = FirebaseAuth.getInstance()

        resetPassword?.setOnClickListener(OnClickListener {
            val useremail =
                passwordEmail?.getText().toString().trim { it <= ' ' }
            if (useremail == "") {
                Toast.makeText(
                    this@PasswordActivity,
                    "Please enter your registered email ID",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                firebaseAuth?.sendPasswordResetEmail(useremail)?.addOnCompleteListener(object:
                    OnCompleteListener<Void?> {
                    override fun onComplete(task: Task<Void?>) {
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@PasswordActivity,
                                "Password reset email sent!",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                            startActivity(
                                Intent(
                                    this@PasswordActivity,
                                    login::class.java
                                )
                            )
                        } else {
                            Toast.makeText(
                                this@PasswordActivity,
                                "Error in sending password reset email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                })
            }
        })
    }
}
