package com.example.shiksha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_after_logout.*
import kotlinx.android.synthetic.main.activity_login.*

class afterLogout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_logout)

        login_again.setOnClickListener(
            View.OnClickListener {
                startActivity(Intent(this,login::class.java))
                finish()
            }
        )
    }
}
