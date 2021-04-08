package com.example.shiksha

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {


    private var Name: EditText? = null
    private var Password: EditText? = null
    private var Info: TextView? = null
    private var login: Button? = null
    private var userRegistration: TextView? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var progressDialog: ProgressDialog? = null
    private var forgotPassword: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Name = findViewById(R.id.etName) as EditText
        Password = findViewById(R.id.etPassword) as EditText

        login = findViewById(R.id.btnLogin) as Button
        userRegistration = findViewById(R.id.tvRegister) as TextView
        forgotPassword = findViewById(R.id.tvForgotPassword) as TextView


        tvRegister.setOnClickListener(
            View.OnClickListener {
                startActivity(Intent(this,Register::class.java))
                finish()
            }
        )

        forgotPassword!!.setOnClickListener {
            startActivity(
                Intent(
                    this@login,
                    PasswordActivity::class.java
                )
            )
        }

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)


        val user = firebaseAuth?.getCurrentUser()

        if (user != null) {
            finish()
            startActivity(Intent(this@login, HomePage::class.java))

        }
        login?.setOnClickListener(View.OnClickListener {
            validate(
                Name?.getText().toString(),
                Password?.getText().toString()
            )
        })

        userRegistration?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@login,
                    Register::class.java
                )
            )
        })

       /* forgotPassword?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@login,
                    PasswordActivity::class.java
                )
            )
        })*/



    }

    private fun validate(userName: String, userPassword: String) {

        progressDialog?.setMessage("Hey! By using this app we can help you to learn better.")
        progressDialog?.show()
        firebaseAuth?.signInWithEmailAndPassword(userName, userPassword)
            ?.addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    progressDialog?.dismiss()
                    //Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerification()

                } else {
                    Toast.makeText(this@login, "Login Failed!", Toast.LENGTH_SHORT).show()
                   // counter--
                   // Info.setText("Number of attempts remaining:$counter")
                    progressDialog?.dismiss()
                    //if (counter == 0) {
                        //login.setEnabled(false)
                    }
                })
            }


        private fun checkEmailVerification() {
            val firebaseUser = firebaseAuth?.getCurrentUser()
            val emailflag = firebaseUser!!.isEmailVerified()

            if (emailflag) {
                finish()
                startActivity(Intent(this@login, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show()
                firebaseAuth?.signOut()
            }
        }

    }



