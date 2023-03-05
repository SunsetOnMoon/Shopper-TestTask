package com.example.shopper

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.shopper.adapter.ProductAdapter
import com.example.shopper.database.DBHelper
import com.example.shopper.databinding.ActivityCatalogBinding
import com.example.shopper.databinding.ActivityMainBinding
import com.example.shopper.model.ProductModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_catalog.*

class CatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatalogBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var dbHandler: DBHelper
    private lateinit var editButton: ImageButton
    private var productList: List<ProductModel> = ArrayList<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()
        fetchList()

        addButton.setOnClickListener{
            val intent = Intent(applicationContext, AddProductActivity::class.java)
            startActivity(intent)
        }

        //fetchList()
    }

    private fun initial() {
        addButton = binding.addButton
        dbHandler = DBHelper(this)
        //adapter.setList(readDatabase())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchList() {
        productList = dbHandler.getAllProducts()
        recyclerView = binding.rvViewCatalog
        adapter = ProductAdapter(productList, applicationContext)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}