package com.example.shopper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.shopper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var editTextLogin: EditText
    private lateinit var editTextPassword: EditText

    private val LOGIN = "admin"
    private val PASSWORD = "admin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextLogin = findViewById(R.id.edit_text_login)
        editTextPassword = findViewById(R.id.edit_text_password)
    }

    fun signupButtonClick(view: View) {
        val login = editTextLogin.text.toString()
        val password = editTextPassword.text.toString()

        if ((login == LOGIN) && (password == PASSWORD))
        {
            val intent = Intent(this@MainActivity, CatalogActivity::class.java)
            startActivity(intent)
        }
        else {
            val invalidToast = Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_SHORT)
            invalidToast.show()
        }
    }
}