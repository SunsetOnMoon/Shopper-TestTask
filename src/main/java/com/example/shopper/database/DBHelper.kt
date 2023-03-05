package com.example.shopper.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.shopper.model.ProductModel

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "shop"
        private val DB_VERSION = 1
        private val TABLE_NAME = "products"
        private val ID = "id"
        private val PRODUCT_NAME = "productname"
        private val PRODUCT_PRICE = "productprice"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $PRODUCT_NAME TEXT, $PRODUCT_PRICE TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Range")
    fun getAllProducts() : List<ProductModel> {
        val productsList = ArrayList<ProductModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val product = ProductModel()
                    product.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    product.name = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME))
                    product.price = cursor.getString(cursor.getColumnIndex(PRODUCT_PRICE))
                    productsList.add(product)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return productsList
    }

    fun addProduct(product: ProductModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PRODUCT_NAME, product.name)
        values.put(PRODUCT_PRICE, product.price)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    @SuppressLint("Range")
    fun getProduct(_id: Int) : ProductModel {
        val product = ProductModel()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        product.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        product.name = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME))
        product.price = cursor.getString(cursor.getColumnIndex(PRODUCT_PRICE))
        cursor.close()
        return product
    }

    fun deleteProduct(_id: Int) : Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun updateProduct(product: ProductModel) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PRODUCT_NAME, product.name)
        values.put(PRODUCT_PRICE, product.price)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(product.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
}