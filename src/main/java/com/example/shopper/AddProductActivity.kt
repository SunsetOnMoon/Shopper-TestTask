package com.example.shopper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.shopper.adapter.ProductAdapter
import com.example.shopper.database.DBHelper
import com.example.shopper.model.ProductModel

class AddProductActivity : AppCompatActivity() {
    lateinit var saveButton: Button
    lateinit var editTextName: EditText
    lateinit var editTextPrice: EditText
    lateinit var dbHandler: DBHelper
    var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        initianal()

        if(intent != null && intent.getStringExtra("Mode") == "E") {
            isEditMode = true
            val product: ProductModel = dbHandler.getProduct(intent.getIntExtra("Id", 0))
            editTextName.setText(product.name)
            editTextPrice.setText(product.price.substring(0, product.price.indexOf('$')))
        } else {
            isEditMode = false
        }

        saveButton.setOnClickListener{
            var success: Boolean = false
            val product: ProductModel = ProductModel()
            if (editTextName.text.isEmpty() || editTextPrice.text.isEmpty())
                Toast.makeText(applicationContext, "Заполнены не все поля!", Toast.LENGTH_LONG)
                    .show()
            else {
                if (isEditMode) {
                    product.id = intent.getIntExtra("Id", 0)
                    product.name = editTextName.text.toString()
                    product.price = editTextPrice.text.toString() + "$"

                    success = dbHandler.updateProduct(product) as Boolean
                } else {
                    product.name = editTextName.text.toString()
                    product.price = editTextPrice.text.toString() + "$"

                    success = dbHandler.addProduct(product) as Boolean
                }
                if (success) {
                    val intent = Intent(applicationContext, CatalogActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Что-то пошло не так!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun initianal() {
        val actionBar = supportActionBar
        actionBar!!.title = "Добавление продукта"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        saveButton = findViewById(R.id.save_button)
        editTextName = findViewById(R.id.edit_text_name)
        editTextPrice = findViewById(R.id.edit_text_price)

        dbHandler = DBHelper(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}