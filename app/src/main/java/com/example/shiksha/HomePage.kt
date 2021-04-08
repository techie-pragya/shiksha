package com.example.shiksha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class HomePage : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null
    private var Logout: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)






            firebaseAuth = FirebaseAuth.getInstance()

            Logout = findViewById(R.id.btnLogout) as Button

            Logout?.setOnClickListener(View.OnClickListener { Logout() })

        }

        private fun Logout() {
            firebaseAuth?.signOut()
            finish()
            startActivity(Intent(this@HomePage, afterLogout::class.java))

        }

        /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
             menuInflater.inflate(R.menu.menu, menu)
             return true
         }*/

        /* override fun onOptionsItemSelected(item: MenuItem): Boolean {

             when (item.itemId) {
                 R.id.logoutMenu -> {
                     Logout()
                 }
             }
             return super.onOptionsItemSelected(item)
         }*/

}
