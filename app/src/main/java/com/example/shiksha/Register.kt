package com.example.shiksha

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class Register : AppCompatActivity() {


    private var userName: EditText? = null
    private var userPassword:EditText? = null
    private var userEmail:EditText? = null
    private var regButton: Button? = null
    private var userLogin: TextView? = null
    private var usernum: EditText? = null
     private var firebaseAuth: FirebaseAuth? = null
    internal var name: String? = null
    internal  var email:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userName = findViewById<EditText>(R.id.etUserName)
        userPassword = findViewById(R.id.etUserPassword) as EditText
        userEmail = findViewById(R.id.etUserEmail) as EditText
        regButton = findViewById(R.id.btnRegister) as Button
        userLogin = findViewById(R.id.tvUserLogin) as TextView

        // var mAuth=FirebaseAuth.getInstance()

        firebaseAuth = FirebaseAuth.getInstance()



        tvUserLogin.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,login::class.java))
            finish()
        })



        regButton?.setOnClickListener(View.OnClickListener {
            if (validate()!!) {
                //upload the data to database
                val user_email = userEmail?.getText().toString().trim { it <= ' ' }
                val user_password = userPassword?.getText().toString().trim { it <= ' ' }

               /* firebaseAuth?.createUserWithEmailAndPassword(user_email, user_password)
                    ?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                      override  fun onComplete(task: Task<AuthResult>) {

                            if (task.isSuccessful()) {

                                firebaseAuth?.currentUser?.sendEmailVerification()?.addOnCompleteListener(
                                    OnCompleteListener {
                                         if (task.isSuccessful){
                                             Toast.makeText(this@Register,"Registration Successful,Check your Email for verification.",Toast.LENGTH_LONG).show()
                                             // startActivity(Intent(this@Register,MainActivity::class.java))
                                             //sendEmailVerification()

                                         }else{
                                             Toast.makeText(this@Register,"Registration failed",Toast.LENGTH_LONG).show()
                                         }
                                    }
                                )

                            } else {
                                Toast.makeText(
                                    this@Register,
                                    "Registration Failed!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    })*/

                firebaseAuth?.createUserWithEmailAndPassword(user_email, user_password)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            sendEmailVerification()

                        } else {
                            Toast.makeText(
                                this@Register,
                                "Registration Failed!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        })


    }

    private fun validate(): Boolean? {
        var result: Boolean? = false

        name = userName?.getText().toString()
        val password = userPassword?.getText().toString()
        email = userEmail?.getText().toString()

        if (name!!.isEmpty() || password.isEmpty() || email!!.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT)
                .show()
        } else {
            result = true

        }
        return result
    }


    var firebaseFireStore =FirebaseFirestore.getInstance()

    private fun sendEmailVerification() {

        val firebaseUser = firebaseAuth?.getCurrentUser()
        firebaseUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
               // sendUserData()//not working
                firebaseFireStore =FirebaseFirestore.getInstance()

                val userdata = HashMap<Any,String>()

                userdata.put("name", userName!!.text.toString())
                firebaseFireStore.collection("Users").add(userdata)
                    .addOnCompleteListener(OnCompleteListener<DocumentReference?> { task ->
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@Register,
                                "Successfully Registered ,Verification mail sent!",
                                Toast.LENGTH_SHORT
                            ).show()
                            firebaseAuth!!.signOut()
                            finish()
                            startActivity(
                                Intent(
                                    this@Register,
                                    MainActivity::class.java
                                )
                            )
                            Toast.makeText(
                                this@Register,
                                "Success in uploading to database",
                                Toast.LENGTH_SHORT
                            ).show()


                        } else {
                            Toast.makeText(
                                this@Register,
                                "Verification mail has'nt been sent!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

            }
        }
    }
}
