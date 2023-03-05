package com.example.shopper.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shopper.AddProductActivity
import com.example.shopper.R
import com.example.shopper.database.DBHelper
import com.example.shopper.model.ProductModel
import kotlinx.android.synthetic.main.item_product_layout.view.*

class ProductAdapter(productList: List<ProductModel>, internal var context: Context)
    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var dbHandler: DBHelper = DBHelper(context)
    internal var productList = emptyList<ProductModel>()
    init {
        this.productList = productList
    }

    inner class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.tv_product_name)
        var price: TextView = view.findViewById(R.id.tv_product_price)
        var deleteButton: ImageButton = view.findViewById(R.id.delete_button)
        var editButton: ImageButton = view.findViewById(R.id.edit_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_layout, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        var product = productList[position]
        holder.itemView.tv_product_name.text = product.name
        holder.itemView.tv_product_price.text = product.price

        holder.editButton.setOnClickListener {
            val intent = Intent(context, AddProductActivity::class.java)
            intent.putExtra("Mode", "E")
            intent.putExtra("Id", product.id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //ломает history stack. Есть ли более элегатное решение?
            context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener{
            val _success = dbHandler.deleteProduct(product.id)
            (productList as ArrayList<ProductModel>).remove(product)
            setList(productList)
            if (!_success)
                Toast.makeText(context, "Что-то пошло не так!", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<ProductModel>) {
        productList = list
        notifyDataSetChanged()
    }
}